### Handler

func (h *TimeEntryHandler) GetDurations(c *gin.Context) {
userIDValue, exists := c.Get("userID")
if !exists {
c.JSON(http.StatusUnauthorized, gin.H{"error": "user not authenticated"})
return
}

	idUser, ok := userIDValue.(uint)
	if !ok {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "invalid user id type"})
		return
	}

	paidParam := c.Query("paid")
	paid := false
	if paidParam != "" {
		p, err := strconv.ParseBool(paidParam)
		if err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": "invalid paid query parameter"})
			return
		}
		paid = p
	}

	descParam := c.Query("desc")
	desc := false
	if descParam != "" {
		d, err := strconv.ParseBool(descParam)
		if err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": "invalid desc query parameter"})
			return
		}
		desc = d
	}

	limit := 20
	page := 1
	if l := c.Query("limit"); l != "" {
		if lInt, err := strconv.Atoi(l); err == nil && lInt > 0 {
			limit = lInt
		} else if l == "-1" {
			limit = -1
		} else {
			c.JSON(http.StatusBadRequest, gin.H{"error": "invalid limit query parameter"})
			return
		}
	}
	if p := c.Query("page"); p != "" {
		if pInt, err := strconv.Atoi(p); err == nil && pInt > 0 {
			page = pInt
		} else {
			c.JSON(http.StatusBadRequest, gin.H{"error": "invalid page query parameter"})
			return
		}
	}

	entries, err := h.service.GetDurations(idUser, paid, desc, limit, page)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, entries)
}

### Repository

// Get the durations, if limit = -1 offset will be as well
func (r *timeEntryRepo) GetDurations(idUser uint, paid bool, desc bool, limit int, page int) ([]model.TimeEntryDuration, error) {
var offset int

	if limit < 0 {
		offset = -1
	} else {
		offset = (page - 1) * limit
	}

	var entries []model.TimeEntry
	err := r.db.
		Preload("CompanyPaymentInfo").
		Where("id_user = ? AND paid = ?", idUser, paid).
		Order(clause.OrderByColumn{
			Column: clause.Column{Name: "clock_in"},
			Desc:   desc,
		}).
		Limit(limit).
		Offset(offset).
		Find(&entries).Error
	if err != nil {
		return nil, err
	}

	results := make([]model.TimeEntryDuration, 0, len(entries))

	// Compute durations
	for _, entry := range entries {
		totalMinutes := uint32(entry.ClockOut.Sub(entry.ClockIn).Minutes())
		hours := totalMinutes / 60
		minutes := totalMinutes % 60

		hourlyRate := 0.0
		if entry.CompanyPaymentInfo != nil {
			hourlyRate = entry.CompanyPaymentInfo.HourlyRate
		}

		results = append(results, model.TimeEntryDuration{
			ID:                   entry.ID,
			ClockIn:              entry.ClockIn,
			ClockOut:             entry.ClockOut,
			TotalDurationMinutes: totalMinutes,
			Hours:                hours,
			Minutes:              minutes,
			HourlyRate:           hourlyRate,
		})
	}

	return results, nil
}