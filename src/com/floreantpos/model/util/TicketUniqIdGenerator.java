package com.floreantpos.model.util;

import org.hibernate.Session;

import com.floreantpos.model.Generator;
import com.floreantpos.model.dao.GenDAO;

/**
 * Unique ID Generator for the Ticket entity.
 * 
 * @author destiny1020
 *
 */
public class TicketUniqIdGenerator {

	private static final String COLUMN_TICKET_UNIQ_ID = "ticketUniqId";
	private static final String TERMINAL_PREFIX = "A";

	public static synchronized String generate() {
		String todayString = DateUtil.getTodayString();

		// get the suffix from DB
		GenDAO instance = GenDAO.getInstance();
		Generator gen = instance.get(COLUMN_TICKET_UNIQ_ID);
		int nextValue = Integer.parseInt(gen.getNextValue());
		gen.setNextValue(String.valueOf(nextValue + 1));
		// update the next value column
		instance.saveOrUpdate(gen);

		return String.format("%s%s%06d", TERMINAL_PREFIX, todayString, nextValue);
	}

}
