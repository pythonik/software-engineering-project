//WindowTemplate.java
//Purpose: This basic window can serve a template for any window that needs to be created.
//			Just make a copy of this class and start working from it.
//Notes: Tried using visual inheritance with this class, however, it stop working with the 
//			WindowBuilder GUI.
package ems.presentation;
import java.util.ArrayList;

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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.SWTResourceManager;

import ems.business.AccessUser;
import ems.business.FinancialCalculator;
import ems.business.CalculateDate;
import ems.models.Employee;

public class FinanceWindow {

	private Shell shlEmsFinance;
	private Display display;
	private FinancialSummaryWindow windowSummary;
	private PaystubWindow paystubWindow;
	
	private final int BOT_PANEL_HEIGHT = 40; //Fixed height of bottom panel
	private final int TOP_PANEL_HEIGHT = 50; //Fixed height of top panel
	
	//Containers for GUI components
	private Composite botPanel; //Lower container, primarily for holding buttons, fixed height
	private Composite midPanel; //Middle container, takes up majority of window, variable height
	private Composite topPanel; //Top container, takes up top row of window, fixed height
	
	//GUI components
	private Button btnQuit;
	private Button btnLogout;
	private Button btnViewPaystub;
	private Button btnSummary;
	private Combo comboUsername;
	private Label lblNumEmployeesValue;
	private Label lblTotalExpendituresValue;
	private Label lblNumEmployees;
	private Label lblTotalExpenditures;
	private Label lblPayPeriod;
	private Label lblPayPeriodValue;
	private FormData fd_botPanel;
	private FormData fd_midPanel;
	private FormData fd_topPanel;
	
	//Data access
	private AccessUser accessUser;
	private ArrayList<Employee> users;

	
	
	
	public FinanceWindow (AccessUser accessUser) {
		this.accessUser = accessUser;
	}
	

	public void open() {
		display = Display.getDefault();
		createContents();
		shlEmsFinance.open();
		shlEmsFinance.layout();
		while (!shlEmsFinance.isDisposed()) {
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
		shlEmsFinance = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.MIN |SWT.MAX);
		shlEmsFinance.setSize(372, 160); //Set dimensions of application window (includes window border)
		shlEmsFinance.setText("EMS - Finance Window");
		shlEmsFinance.setLayout(new FormLayout());
		shlEmsFinance.setImage(SWTResourceManager.getImage(LogInWindow.class, "/resources/EMSLogo.jpg"));
	}
	
	private void createPanels() {	
		createTopPanel();
		createBotPanel();
		createMidPanel();
	}
	
	private void createTopPanel() {
		topPanel = new Composite(shlEmsFinance, SWT.NONE);
		topPanel.setLayout(null);
		
		fd_topPanel = new FormData();
		fd_topPanel.top = new FormAttachment(0,0);
		fd_topPanel.bottom = new FormAttachment(0, +TOP_PANEL_HEIGHT);
		fd_topPanel.right = new FormAttachment(100,0);
		fd_topPanel.left = new FormAttachment(0,0);
		topPanel.setLayoutData(fd_topPanel);
	}
	
	private void createMidPanel() {
		midPanel = new Composite(shlEmsFinance, SWT.NONE);
		midPanel.setLayout(null);
		
		fd_midPanel = new FormData(); 
		fd_midPanel.top = new FormAttachment(0,+TOP_PANEL_HEIGHT);
		fd_midPanel.bottom = new FormAttachment(100, -BOT_PANEL_HEIGHT);
		fd_midPanel.right = new FormAttachment(100,0);
		fd_midPanel.left = new FormAttachment(0,0);
		midPanel.setLayoutData(fd_midPanel);		
	}
	
	private void createBotPanel() {
		botPanel = new Composite(shlEmsFinance, SWT.NONE);
		botPanel.setLayout(null);

		fd_botPanel = new FormData(); 
		fd_botPanel.top = new FormAttachment(100, -BOT_PANEL_HEIGHT);
		fd_botPanel.right = new FormAttachment(100, 0);
		fd_botPanel.left = new FormAttachment(0,0);
		fd_botPanel.bottom = new FormAttachment(100, 0);
		botPanel.setLayoutData(fd_botPanel);
	}
	
	
	private void createComponents() {
		createLabels();
		createButtons();
		createCombo();
		fillValues();
	}
	
	private void fillValues() {
		lblNumEmployeesValue.setText(Integer.toString(users.size()));
		lblPayPeriodValue.setText(CalculateDate.getLatestPayPeriod());
		lblTotalExpendituresValue.setText("$" + FinancialCalculator.getTotalExpenditures());
	}
	
	private void createLabels() {
		lblPayPeriod = new Label(topPanel, SWT.NONE);
		lblPayPeriod.setBounds(38, 3, 95, 15);
		lblPayPeriod.setText("Latest Pay Period:");
		
		lblNumEmployees = new Label(topPanel, SWT.NONE);
		lblNumEmployees.setBounds(10, 18, 120, 15);
		lblNumEmployees.setText("Number of Employees:");
		
		lblTotalExpenditures = new Label(topPanel, SWT.NONE);
		lblTotalExpenditures.setBounds(31, 33, 100, 15);
		lblTotalExpenditures.setText("Total Expenditures:");
		
		lblPayPeriodValue = new Label(topPanel, SWT.NONE);
		lblPayPeriodValue.setBounds(143, 3, 137, 15);
		lblPayPeriodValue.setText("New Label");
		
		lblNumEmployeesValue = new Label(topPanel, SWT.NONE);
		lblNumEmployeesValue.setBounds(143, 18, 44, 15);
		
		lblTotalExpendituresValue = new Label(topPanel, SWT.NONE);
		lblTotalExpendituresValue.setBounds(143, 33, 55, 15);
	}
	
	private void createCombo() {
		
		comboUsername = new Combo(midPanel, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboUsername.setBounds(157, 10, 120, 23);
		comboUsername.select(0);
		
		//Populate ComboBox with usernames in database
		users = accessUser.getUserByType("E");
		for(int i = 0; i < users.size(); i++){
			comboUsername.add(users.get(i).getUsername() + " (" + users.get(i).getEmployeeType() + ")");
		}
		comboUsername.select(0);
	}
	
	private void createButtons() {
		
		btnSummary = new Button(topPanel, SWT.NONE);
		btnSummary.setBounds(283, 3, 80, TOP_PANEL_HEIGHT - 6);
		btnSummary.setText("Summary");
		btnSummary.addSelectionListener(new SelectionAdapter() { 
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shlEmsFinance.setEnabled(false);
				windowSummary = new FinancialSummaryWindow();
				windowSummary.open();
				shlEmsFinance.setEnabled(true);
				shlEmsFinance.forceActive();
			}
		});
		
		btnViewPaystub = new Button(midPanel, SWT.NONE);
		btnViewPaystub.setBounds(283, 4, 80, 32);
		btnViewPaystub.setText("Paystub");
		btnViewPaystub.addSelectionListener(new SelectionAdapter() { 
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				AccessUser selectedUser = new AccessUser();
				if(comboUsername.getItemCount() > 0){
					selectedUser.selectUser(users.get(comboUsername.getSelectionIndex()));
					paystubWindow = new PaystubWindow(selectedUser);
					shlEmsFinance.setEnabled(false);
					paystubWindow.open();
					shlEmsFinance.setEnabled(true);
					shlEmsFinance.forceActive();
				}
			}
		});		
		
		btnQuit = new Button(botPanel, SWT.NONE);
		btnQuit.setBounds(3, 3, 75, 32);
		btnQuit.addSelectionListener(new SelectionAdapter() { 
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shlEmsFinance.close();
			}
		});
		btnQuit.setText("Quit");
		
		btnLogout = new Button(botPanel, SWT.NONE);
		btnLogout.setBounds(81, 3, 75, 32);
		btnLogout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				accessUser.logOutUser();
				shlEmsFinance.close();
				LogInWindow logInWindow = new LogInWindow(accessUser);
				logInWindow.open();
			}
		});
		btnLogout.setText("Log Out");
	}
}
