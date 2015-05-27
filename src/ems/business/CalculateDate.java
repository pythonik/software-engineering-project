package ems.business;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalculateDate{
	private static int PAYDAY_OF_MONTH = 15;
	private static int MAX_MINUTES_IN_FUTURE = 15;
	private static int MAX_DAYS_IN_PAST = 7;
	
	public static boolean timesheetDateInBounds(Calendar date){

		boolean inBounds = false;
		GregorianCalendar timeUpperBound = new GregorianCalendar();
		GregorianCalendar timeLowerBound = new GregorianCalendar();
		
		if(date != null){
			timeUpperBound.add(Calendar.MINUTE, MAX_MINUTES_IN_FUTURE);
			timeLowerBound.add(Calendar.DAY_OF_WEEK, -MAX_DAYS_IN_PAST);		
			
			if(date.before(timeUpperBound) && date.after(timeLowerBound)){
				inBounds = true;			
			}
		}

		return inBounds;
	}
	
	public static String getLatestPayPeriod() {
		SimpleDateFormat medFormat = new SimpleDateFormat("MMM dd, yyyy");
		GregorianCalendar start, end;
		String result;
		
		start = getPayPeriodStart(new GregorianCalendar());
		end = getPayPeriodEnd(new GregorianCalendar());
		
		result = medFormat.format(start.getTime()) +
				" - " + 
				medFormat.format(end.getTime());

		return result;
	}
	
	public static GregorianCalendar getPayPeriodStart(GregorianCalendar referenceDate) {

		GregorianCalendar payPeriodStart = (GregorianCalendar)referenceDate.clone();
		setToMidnight(payPeriodStart);
		
		if(payPeriodStart.get(Calendar.DAY_OF_MONTH) >= PAYDAY_OF_MONTH){
			payPeriodStart.set(Calendar.DAY_OF_MONTH, 1);
		}
		else if(payPeriodStart.get(Calendar.DAY_OF_MONTH) < PAYDAY_OF_MONTH){
			payPeriodStart.add(Calendar.MONTH, -1);
			payPeriodStart.set(Calendar.DAY_OF_MONTH, PAYDAY_OF_MONTH);
		}
		return payPeriodStart;
	}
	
	//Calculates the end date of the most recently completed pay period, relative to referenceDate
	public static GregorianCalendar getPayPeriodEnd(GregorianCalendar referenceDate) {

		GregorianCalendar payPeriodEnd = (GregorianCalendar)referenceDate.clone();
		setToMidnight(payPeriodEnd);

		if(payPeriodEnd.get(Calendar.DAY_OF_MONTH) >= PAYDAY_OF_MONTH){
			payPeriodEnd.set(Calendar.DAY_OF_MONTH, PAYDAY_OF_MONTH);
		}
		else if(payPeriodEnd.get(Calendar.DAY_OF_MONTH) < PAYDAY_OF_MONTH){
			payPeriodEnd.add(Calendar.MONTH, -1);
			payPeriodEnd.set(Calendar.DAY_OF_MONTH, payPeriodEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		return payPeriodEnd;
	}
	
	public static void setToMidnight(GregorianCalendar date){
		
		if(date != null){
			date.set(Calendar.AM_PM, Calendar.PM);
			date.set(Calendar.HOUR_OF_DAY, 11);
			date.set(Calendar.MINUTE, 59);
			date.set(Calendar.SECOND, 59);	
		}

	}
}