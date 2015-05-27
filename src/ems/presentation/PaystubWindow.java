package ems.presentation;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.Font;
import org.eclipse.wb.swt.SWTResourceManager;

import ems.business.AccessUser;
import ems.business.CalculateHours;
import ems.business.CalculateDate;



public class PaystubWindow {

	private Shell shlEmsPaystub;
	private Display display;
	
	private final int BOT_PANEL_HEIGHT = 40;
	
	//Containers for GUI components
	private Composite botPanel;
	private Composite midPanel;
	
	//GUI components
	private Button btnClose;
	private Button btnPrint;
	private Label lblEmployeeName;
	private Label lblPayPeriod;
	private Label lblHoursWorked;
	private Label lblAmountEarned;
	private Label lblWage;
	
	//Data Access
	private AccessUser accessUser;
	
	
	
	public PaystubWindow(AccessUser accessUser) {
		this.accessUser = accessUser;
	}

	public void open() {
		display = Display.getDefault();
		createContents();
		shlEmsPaystub.open();
		shlEmsPaystub.layout();
		while (!shlEmsPaystub.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		
		createAppWindow();
		createPanels();
		createComponents();

	}
	
	private void createAppWindow() {
		shlEmsPaystub = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.MIN);
		shlEmsPaystub.setSize(400, 290);
		shlEmsPaystub.setText("EMS - Employee Paystub");
		shlEmsPaystub.setLayout(new FormLayout());
		shlEmsPaystub.setImage(SWTResourceManager.getImage(PaystubWindow.class, "/resources/EMSLogo.jpg"));
	}
	
	private void createPanels() {	
		createBotPanel();
		createMidPanel();
	}
	
	private void createMidPanel() {
		midPanel = new Composite(shlEmsPaystub, SWT.NONE);
		
		FormData fd_midPanel = new FormData(); 
		fd_midPanel.top = new FormAttachment(0);
		fd_midPanel.bottom = new FormAttachment(100, -BOT_PANEL_HEIGHT);
		fd_midPanel.right = new FormAttachment(100,0);
		fd_midPanel.left = new FormAttachment(0,0);
		midPanel.setLayoutData(fd_midPanel);		
	}
	
	private void createBotPanel() {
		botPanel = new Composite(shlEmsPaystub, SWT.NONE);
		botPanel.setLayout(null);

		FormData fd_botPanel = new FormData(); 
		fd_botPanel.top = new FormAttachment(100, -BOT_PANEL_HEIGHT);
		fd_botPanel.right = new FormAttachment(100, 0);
		fd_botPanel.left = new FormAttachment(0,0);
		fd_botPanel.bottom = new FormAttachment(100, 0);
		botPanel.setLayoutData(fd_botPanel);
	}
	
	
	private void createComponents() {
		createButtons();
		createLabels();
	}
	
	private void createButtons() {
		btnClose = new Button(botPanel, SWT.NONE);
		btnClose.setBounds(3, 3, 75, 32);
		btnClose.addSelectionListener(new SelectionAdapter() { 
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shlEmsPaystub.close();
			}
		});
		btnClose.setText("Close");
		
		btnPrint = new Button(botPanel, SWT.NONE);
		btnPrint.setBounds(316, 3, 75, 32);
		btnPrint.setText("Print");
		btnPrint.setEnabled(false);
	}
	
	private void createLabels() {
		int hours = CalculateHours.getEmployeeHoursInPeriod(accessUser.getCurrentUser());
		
		lblEmployeeName = new Label(midPanel, SWT.NONE);
		lblEmployeeName.setText("Employee: " + accessUser.getCurrentUserName());
		lblEmployeeName.setFont(new Font(display, "Times", 14, SWT.BOLD));
		lblEmployeeName.setBounds(55, 10, 329, 28);
		
		lblPayPeriod = new Label(midPanel, SWT.NONE);
		lblPayPeriod.setText("Pay Period: " + CalculateDate.getLatestPayPeriod());
		lblPayPeriod.setFont(new Font(display, "Times", 14, SWT.BOLD));
		lblPayPeriod.setBounds(46, 50, 338, 28);
		
		lblHoursWorked = new Label(midPanel, SWT.NONE);
		lblHoursWorked.setText("Hours Worked: " + hours);
		lblHoursWorked.setFont(new Font(display, "Times", 14, SWT.BOLD));
		lblHoursWorked.setBounds(17, 90, 367, 28);
		
		lblWage = new Label(midPanel, SWT.NONE);
		lblWage.setText("Wage: $" + accessUser.getCurrentUser().getRate() +"/hr");
		lblWage.setFont(new Font(display, "Times", 14, SWT.BOLD));
		lblWage.setBounds(90, 130, 294, 28);
		
		lblAmountEarned = new Label(midPanel, SWT.NONE);
		lblAmountEarned.setText("Amount Earned: $" + (hours * accessUser.getCurrentUser().getRate()));
		lblAmountEarned.setFont(new Font(display, "Times", 14, SWT.BOLD));
		lblAmountEarned.setBounds(10, 170, 374, 28);
	}
}
