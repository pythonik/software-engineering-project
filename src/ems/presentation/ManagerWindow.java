package ems.presentation;
import java.util.ArrayList;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Label;
import ems.business.AccessUser;
import ems.business.AccessTimesheet;
import ems.models.Employee;
import ems.models.Manager;
import ems.models.Notification;
import ems.models.Timesheet;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wb.swt.SWTResourceManager;

public class ManagerWindow {

	//Window objects
	private Shell shell;
	private Display display;
	
	//Layout constants
	private final int BOT_PANEL_HEIGHT = 40;
	private final int TOP_PANEL_HEIGHT = 50; 
	private final int TABLE_PADDING = 5; 
	
	//Containers for GUI components
	private Composite botPanel;
	private Composite midPanel;
	private Composite topPanel;
	
	//GUI components
	private Button btnQuit;
	private Button btnLogOut;
	private Button btnEmployeeDetails;
	private Button btnOverall;
	private Button btnViewNotifications;
	private Label lblCurrentUsername;
	private Label lblMyEmployees;
	private Label lblLoggedInAs;
	private Label lblPendingNotifications;
	private Table tblTimesheet;
	private Combo comboEmployeeList;
	
	//Data Access
	private AccessUser accessUser;
	private AccessUser accessEmployee;
	private AccessTimesheet accessTimesheet;
	private ArrayList<Employee> employees;
	private ArrayList<Timesheet> timesheets;
	private FormData fd_lblMyEmployees;
	ArrayList<Notification> notifications;
	
	public ManagerWindow(AccessUser accessUser) {
		this.accessUser = accessUser;
		employees = new ArrayList<Employee>();
		notifications = ((Manager)accessUser.getCurrentUser()).getNotifications();
	}
	
	public void open() {
		display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
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
		shell = new Shell();
		shell.setMinimumSize(new Point(500, 300));
		shell.setSize(519, 300); 
		shell.setText("EMS - Manager Window");
		shell.setLayout(new FormLayout());
		shell.setImage(SWTResourceManager.getImage(ManagerWindow.class, "/resources/EMSLogo.jpg"));
	}
	
	private void createPanels() {
		createTopPanel();
		createMidPanel();
		createBotPanel();
	}
	
	private void createTopPanel() {
		topPanel = new Composite(shell, SWT.NONE);
		topPanel.setLayout(new FormLayout());
		
		FormData fd_topPanel = new FormData(); 
		fd_topPanel.top = new FormAttachment(0,0);
		fd_topPanel.bottom = new FormAttachment(0, +TOP_PANEL_HEIGHT);
		fd_topPanel.right = new FormAttachment(100,0);
		fd_topPanel.left = new FormAttachment(0,0);
		topPanel.setLayoutData(fd_topPanel);
	}
	
	private void createMidPanel() {
		midPanel = new Composite(shell, SWT.NONE);
		midPanel.setLayout(new FormLayout());
		
		FormData fd_midPanel = new FormData(); 
		fd_midPanel.top = new FormAttachment(0,+TOP_PANEL_HEIGHT);
		fd_midPanel.bottom = new FormAttachment(100, -BOT_PANEL_HEIGHT);
		fd_midPanel.right = new FormAttachment(100,0);
		fd_midPanel.left = new FormAttachment(0,0);
		midPanel.setLayoutData(fd_midPanel);
	}
	
	private void createBotPanel() {
		botPanel = new Composite(shell, SWT.NONE);
		botPanel.setLayout(new FormLayout());

		FormData fd_botPanel = new FormData(); 
		fd_botPanel.top = new FormAttachment(100, -BOT_PANEL_HEIGHT);
		fd_botPanel.right = new FormAttachment(100, 0);
		fd_botPanel.left = new FormAttachment(0,0);
		fd_botPanel.bottom = new FormAttachment(100, 0);
		botPanel.setLayoutData(fd_botPanel);
	}
	
	private void createComponents() {
		createLabels();
		createDropDowns();
		createButtons();
		createTable();
	}
	
	private void createTable() {
		tblTimesheet = new Table(midPanel, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		tblTimesheet.setHeaderVisible(true);
		tblTimesheet.setLinesVisible(true);
		
		FormData fd_tblTimesheet = new FormData(); 
		fd_tblTimesheet.top = new FormAttachment(0, TABLE_PADDING);
		fd_tblTimesheet.right = new FormAttachment(100, midPanel.getSize().x - TABLE_PADDING);
		fd_tblTimesheet.left = new FormAttachment(0, TABLE_PADDING);
		fd_tblTimesheet.bottom = new FormAttachment(100, midPanel.getSize().y - TABLE_PADDING);
		tblTimesheet.setLayoutData(fd_tblTimesheet);
		
	    String[] titles = { "Start Date","End Date","Start Time", "End Time", "Name", "Project", "Hours Worked"};
        for (int i = 0; i < titles.length; i++) {
          TableColumn column = new TableColumn(tblTimesheet, SWT.NONE);
          column.setText(titles[i]);
        }
        
        populateTable();
        
        for (int i = 0; i < titles.length; i++) {
            tblTimesheet.getColumn(i).pack();
        }

	}
	
	private void createLabels() {

		lblLoggedInAs = new Label(topPanel, SWT.NONE);
		FormData fd_lblLoggedInAs = new FormData();
		fd_lblLoggedInAs.right = new FormAttachment(0, 85);
		fd_lblLoggedInAs.top = new FormAttachment(0, 5);
		fd_lblLoggedInAs.left = new FormAttachment(0, 5);
		lblLoggedInAs.setLayoutData(fd_lblLoggedInAs);
		lblLoggedInAs.setText("Logged in as:");
		
		lblCurrentUsername = new Label(topPanel, SWT.NONE);
		FormData fd_lblCurrentUsername = new FormData();
		fd_lblCurrentUsername.bottom = new FormAttachment(0, 42);
		fd_lblCurrentUsername.right = new FormAttachment(0, 125);
		fd_lblCurrentUsername.top = new FormAttachment(0, 20);
		fd_lblCurrentUsername.left = new FormAttachment(0, 5);
		lblCurrentUsername.setLayoutData(fd_lblCurrentUsername);
		lblCurrentUsername.setFont(new Font(display, "Times", 14, SWT.BOLD));
		lblCurrentUsername.setText(accessUser.getCurrentUserName());
		

		lblPendingNotifications = new Label(botPanel, SWT.NONE);
		lblPendingNotifications.setFont(new Font(display, "Times", 14, SWT.BOLD));
		lblPendingNotifications.setText(notifications.size()+" Notification"+(notifications.size()==1?"":"s"));
		if(notifications.size() > 0){
			lblPendingNotifications.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
		}
		
		
		lblMyEmployees = new Label(topPanel, SWT.NONE);
		fd_lblMyEmployees = new FormData();
		fd_lblMyEmployees.top = new FormAttachment(lblLoggedInAs, 0, SWT.TOP);
		fd_lblMyEmployees.left = new FormAttachment(lblLoggedInAs, 115);
		lblMyEmployees.setLayoutData(fd_lblMyEmployees);
		lblMyEmployees.setText("My employees:");
	}
	
	private void createDropDowns() {
		
		comboEmployeeList = new Combo(topPanel, SWT.READ_ONLY);
		FormData fd_comboEmployeeList = new FormData();
		fd_comboEmployeeList.left = new FormAttachment(lblMyEmployees, 0, SWT.LEFT);
		fd_comboEmployeeList.right = new FormAttachment(100, -188);
		fd_comboEmployeeList.top = new FormAttachment(0, 25);
		fd_comboEmployeeList.bottom = new FormAttachment(0, 150);
		comboEmployeeList.setLayoutData(fd_comboEmployeeList);
		employees = accessUser.getTeamMember();
		
		for(int i = 0; i< employees.size(); i++){
			comboEmployeeList.add(employees.get(i).getUsername());
		}
		comboEmployeeList.select(0);
		
		accessEmployee = new AccessUser();
		if(comboEmployeeList.getItemCount() > 0){
			accessEmployee.selectUser(employees.get(comboEmployeeList.getSelectionIndex()));
		}
		
		
		//Refreshes table with new data
		comboEmployeeList.addSelectionListener(new SelectionAdapter() { 
			@Override
			public void widgetSelected(SelectionEvent arg0) {		
				accessEmployee.logOutUser();
				accessEmployee.selectUser(employees.get(comboEmployeeList.getSelectionIndex()));
				accessTimesheet = new AccessTimesheet(employees.get(comboEmployeeList.getSelectionIndex()));
				timesheets = accessTimesheet.getCurrentUserTimesheet();

				tblTimesheet.clearAll();
				tblTimesheet.removeAll();
				populateTable();
			}
		});
		if(comboEmployeeList.getItemCount() > 0){
			accessTimesheet = new AccessTimesheet(employees.get(comboEmployeeList.getSelectionIndex()));
			timesheets = accessTimesheet.getCurrentUserTimesheet();
		}
		else{
			timesheets = new ArrayList<Timesheet>();
		}
		
	}
	
	private void populateTable() {
		for(int i = 0; i < timesheets.size(); i++) {
        	Timesheet currTimesheet = timesheets.get(i);
            TableItem item = new TableItem(tblTimesheet, SWT.NONE);
            
            item.setText(0, currTimesheet.getStartDateString());
            item.setText(1, currTimesheet.getEndDateString());
            item.setText(2, currTimesheet.getStartTimeString());
            item.setText(3, currTimesheet.getEndTimeString());
            item.setText(4, accessEmployee.getCurrentUserName());
            item.setText(5, currTimesheet.getProjectName());
            item.setText(6, Float.toString(currTimesheet.getHours()));
        }
		
	}
	
	private void createButtons() {
		btnQuit = new Button(botPanel, SWT.NONE);
		FormData fd_btnQuit = new FormData();
		fd_btnQuit.top = new FormAttachment(0, 5);
		fd_btnQuit.left = new FormAttachment(0, 5);
		fd_btnQuit.right = new FormAttachment(0, 85);
		fd_btnQuit.bottom = new FormAttachment(100, -5);
		btnQuit.setLayoutData(fd_btnQuit);
		btnQuit.addSelectionListener(new SelectionAdapter() { 
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
			}
		});
		btnQuit.setText("Quit");
		
		btnLogOut = new Button(botPanel, SWT.NONE);
		fd_btnQuit.bottom = new FormAttachment(btnLogOut, 0, SWT.BOTTOM);
		FormData fd_btnLogOut = new FormData();
		fd_btnLogOut.right = new FormAttachment(0, 175);
		fd_btnLogOut.left = new FormAttachment(0, 90);
		fd_btnLogOut.bottom = new FormAttachment(100, -5);
		fd_btnLogOut.top = new FormAttachment(0, 5);
		btnLogOut.setLayoutData(fd_btnLogOut);
		btnLogOut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				accessUser.logOutUser();
				shell.close();
				LogInWindow logInWindow = new LogInWindow(accessUser);
				logInWindow.open();
			}
		});
		btnLogOut.setText("Log Out");
		
		
		
		
		btnViewNotifications = new Button(botPanel, SWT.NONE);
		btnViewNotifications.setEnabled(false);
		FormData fd_lblPendingNotifications = new FormData();
		fd_lblPendingNotifications.right = new FormAttachment(btnViewNotifications, -14);
		fd_lblPendingNotifications.top = new FormAttachment(btnQuit, 2, SWT.TOP);
		lblPendingNotifications.setLayoutData(fd_lblPendingNotifications);
		FormData fd_btnViewNotifications = new FormData();
		fd_btnViewNotifications.width = 150;
		fd_btnViewNotifications.right = new FormAttachment(100, -10);
		fd_btnViewNotifications.bottom = new FormAttachment(btnQuit, 0, SWT.BOTTOM);
		fd_btnViewNotifications.top = new FormAttachment(btnQuit, -5, SWT.TOP);
		btnViewNotifications.setLayoutData(fd_btnViewNotifications);
		btnViewNotifications.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (notifications != null){
					//System.out.println("Notifications for Manager: "+accessUser.getCurrentUserName());
					for(@SuppressWarnings("unused") Notification notification:notifications){
						//System.out.println(notification);
					}
				}
			}
		});
		btnViewNotifications.setText("View Notifications");
		
		btnEmployeeDetails = new Button(topPanel, SWT.NONE);
		fd_lblMyEmployees.right = new FormAttachment(100, -193);
		btnEmployeeDetails.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				AccessUser selectedEmployee = new AccessUser();
				if(comboEmployeeList.getItemCount() > 0){
					selectedEmployee.selectUser(employees.get(comboEmployeeList.getSelectionIndex()));
					
					DetailsWindow detailsWindow = new DetailsWindow(selectedEmployee);
					shell.setEnabled(false);
					detailsWindow.open();
					shell.setEnabled(true);
					shell.forceActive();
				}
				
			}
		});
		
		FormData fd_btnEmployeeDetails = new FormData();
		fd_btnEmployeeDetails.top = new FormAttachment(lblLoggedInAs, 0, SWT.TOP);
		fd_btnEmployeeDetails.left = new FormAttachment(comboEmployeeList, 10);
		fd_btnEmployeeDetails.right = new FormAttachment(100, -98);
		fd_btnEmployeeDetails.bottom = new FormAttachment(0, 50);
		btnEmployeeDetails.setLayoutData(fd_btnEmployeeDetails);
		btnEmployeeDetails.setText("Detail");
		
		btnOverall = new Button(topPanel, SWT.NONE);
		btnOverall.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				WorkReportWindow reportWindow = new WorkReportWindow();
				shell.setEnabled(false);
				reportWindow.open();
				shell.setEnabled(true);
				shell.forceActive();
			}
		});
		FormData fd_btnOverall = new FormData();
		fd_btnOverall.right = new FormAttachment(100, -10);
		fd_btnOverall.bottom = new FormAttachment(comboEmployeeList, -100, SWT.BOTTOM);
		fd_btnOverall.top = new FormAttachment(lblLoggedInAs, 0, SWT.TOP);
		fd_btnOverall.left = new FormAttachment(btnEmployeeDetails, 8);
		btnOverall.setLayoutData(fd_btnOverall);
		btnOverall.setText("Overall");
		if(notifications.size()==0){
			btnViewNotifications.setEnabled(false);
		}
	}
}
