package NotifcationDetails;

import java.time.LocalDateTime;


public class SMS extends Notification{


    public SMS() {
		super();
	}

	public static void sendTicketConfirmation() {
		new SMS();
        // Code to send ticket confirmation email to recipientEmail
		System.out.println("Time "+LocalDateTime.now());
        System.out.println("Ticket confirmation SMS sent  " );
    }
}
