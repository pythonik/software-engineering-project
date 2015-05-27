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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.graphics.Point;

import ems.business.AccessUser;
import ems.business.AccessTimesheet;
import ems.models.Employee;
import ems.models.Timesheet;

import java.util.ArrayList;
import org.eclipse.wb.swt.SWTResourceManager;

public class EmployeeWindow {

	//Window objects
	private Shell shlEmsEmployee;
	private Display display;
	
	//Layout constants
	private final int BUTTON_PANEL_HEIGHT = 40;
	private final int TOP_PANEL_HEIGHT = 50; 
	
	//Containers for GUI components
	private Composite botPanel;
	private Composite midPanel;
	private Composite topPanel; 
	
	//GUI components
	private Button btnQuit;
	private Button btnLogout;
	private Button btnViewDetails;
	private Button btnCreateTimesheet;
	private Table table;
	private Label lblLoggedInAs;
	private Label lblUsername;

	//Data Access
	private AccessUser accessUser;
	private AccessTimesheet accessTimesheet;
	private ArrayList<Timesheet> timesheets;
	
	public EmployeeWindow (AccessUser accessUser) {
		this.accessUser = accessUser;
		accessTimesheet = new AccessTimesheet((Employee)accessUser.getCurrentUser());
	}
	
	private EmployeeWindow getSelf(){
		return this;
	}
	

	public void open() {
		display = Display.getDefault();
		createContents();
		shlEmsEmployee.open();
		shlEmsEmployee.layout();
		while (!shlEmsEmployee.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	protected void redraw() {
		shlEmsEmployee.layout();
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
		shlEmsEmployee = new Shell();
		shlEmsEmployee.setMinimumSize(new Point(450, 225));
		shlEmsEmployee.setSize(513, 300);
		shlEmsEmployee.setText("EMS - Employee");
		shlEmsEmployee.setLayout(new FormLayout());
		shlEmsEmployee.setImage(SWTResourceManager.getImage(EmployeeWindow.class, "/resources/EMSLogo.jpg"));
	}
	
	private void createPanels() {
		createTopPanel();
		createMidPanel();
		createBotPanel();
	}
	
	private void createTopPanel(){
		topPanel = new Composite(shlEmsEmployee, SWT.NONE);
		topPanel.setLayout(new FormLayout());
		
		FormData fd_topPanel = new FormData(); 
		fd_topPanel.top = new FormAttachment(0,0);
		fd_topPanel.bottom = new FormAttachment(0, +TOP_PANEL_HEIGHT);
		fd_topPanel.right = new FormAttachment(100,0);
		fd_topPanel.left = new FormAttachment(0,0);
		topPanel.setLayoutData(fd_topPanel);
	}
	
	private void createMidPanel(){
		midPanel = new Composite(shlEmsEmployee, SWT.NONE);
		midPanel.setLayout(new FormLayout());
		
		FormData fd_midPanel = new FormData(); 
		fd_midPanel.top = new FormAttachment(0,+TOP_PANEL_HEIGHT);
		fd_midPanel.bottom = new FormAttachment(100, -BUTTON_PANEL_HEIGHT);
		fd_midPanel.right = new FormAttachment(100,0);
		fd_midPanel.left = new FormAttachment(0,0);
		midPanel.setLayoutData(fd_midPanel);
	}
	
	private void createBotPanel(){
		botPanel = new Composite(shlEmsEmployee, SWT.NONE);
		botPanel.setLayout(new FormLayout());

		FormData fd_botPanel = new FormData(); 
		fd_botPanel.top = new FormAttachment(100, -BUTTON_PANEL_HEIGHT);
		fd_botPanel.right = new FormAttachment(100, 0);
		fd_botPanel.left = new FormAttachment(0,0);
		fd_botPanel.bottom = new FormAttachment(100, 0);
		botPanel.setLayoutData(fd_botPanel);
	}
	
	private void createComponents() {
		createButtons();
		createLabels();
		createTable();
	}
	
	private void createButtons() {

		btnQuit = new Button(botPanel, SWT.NONE);
		FormData fd_btnQuit = new FormData();
		fd_btnQuit.bottom = new FormAttachment(0, 35);
		fd_btnQuit.right = new FormAttachment(0, 78);
		fd_btnQuit.top = new FormAttachment(0, 3);
		fd_btnQuit.left = new FormAttachment(0, 3);
		btnQuit.setLayoutData(fd_btnQuit);
		btnQuit.addSelectionListener(new SelectionAdapter() { 
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shlEmsEmployee.close();
			}
		});
		btnQuit.setText("Quit");
		
		btnLogout = new Button(botPanel, SWT.NONE);
		FormData fd_btnLogout = new FormData();
		fd_btnLogout.bottom = new FormAttachment(0, 35);
		fd_btnLogout.right = new FormAttachment(0, 156);
		fd_btnLogout.top = new FormAttachment(0, 3);
		fd_btnLogout.left = new FormAttachment(0, 81);
		btnLogout.setLayoutData(fd_btnLogout);
		btnLogout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shlEmsEmployee.close();
				LogInWindow loginWindow = new LogInWindow();
				loginWindow.open();
			}
		});
		btnLogout.setText("Log Out");
		
		btnCreateTimesheet = new Button(botPanel, SWT.NONE);
		FormData fd_btnCreateTimesheet = new FormData();
		fd_btnCreateTimesheet.bottom = new FormAttachment(0, 35);
		fd_btnCreateTimesheet.right = new FormAttachment(100, -3);
		fd_btnCreateTimesheet.top = new FormAttachment(0, 3);
		fd_btnCreateTimesheet.left = new FormAttachment(100, -78);
		btnCreateTimesheet.setLayoutData(fd_btnCreateTimesheet);
		btnCreateTimesheet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				NewTimesheetWindow timesheetWindow = new NewTimesheetWindow(accessUser, getSelf());
				shlEmsEmployee.setEnabled(false);
				timesheetWindow.open();
				shlEmsEmployee.setEnabled(true);
				shlEmsEmployee.forceActive();
			}
		});
		btnCreateTimesheet.setText("New");
		
		btnViewDetails = new Button(topPanel, SWT.NONE);
		FormData fd_btnViewDetails = new FormData();
		fd_btnViewDetails.bottom = new FormAttachment(0, 50);
		fd_btnViewDetails.top = new FormAttachment(0, 5);
		fd_btnViewDetails.left = new FormAttachment(100, -78);
		fd_btnViewDetails.right = new FormAttachment(100, -3);
		btnViewDetails.setLayoutData(fd_btnViewDetails);
		btnViewDetails.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DetailsWindow detailsWindow = new DetailsWindow(accessUser);
				shlEmsEmployee.setEnabled(false);
				detailsWindow.open();
				shlEmsEmployee.setEnabled(true);
				shlEmsEmployee.forceActive();
			}
		});
		btnViewDetails.setText("Details");
	}
	
	private void createLabels() {
		
		lblLoggedInAs = new Label(topPanel, SWT.NONE);
		FormData fd_lblLoggedInAs = new FormData();
		fd_lblLoggedInAs.right = new FormAttachment(0, 85);
		fd_lblLoggedInAs.top = new FormAttachment(0, 5);
		fd_lblLoggedInAs.left = new FormAttachment(0, 5);
		lblLoggedInAs.setLayoutData(fd_lblLoggedInAs);
		lblLoggedInAs.setText("Logged In As");
		
		lblUsername = new Label(topPanel, SWT.NONE);
		FormData fd_lblUsername = new FormData();
		fd_lblUsername.bottom = new FormAttachment(0, 42);
		fd_lblUsername.right = new FormAttachment(0, 125);
		fd_lblUsername.top = new FormAttachment(0, 20);
		fd_lblUsername.left = new FormAttachment(0, 5);
		lblUsername.setLayoutData(fd_lblUsername);
		lblUsername.setFont(new Font(display, "Times", 14, SWT.BOLD));
		lblUsername.setText(accessUser.getCurrentUserName());
	}
	
	private void createTable() {
		table = new Table(midPanel, SWT.BORDER | SWT.FULL_SELECTION);
		FormData fd_table = new FormData();
		fd_table.bottom = new FormAttachment(100, -3);
		fd_table.right = new FormAttachment(100, -3);
		fd_table.top = new FormAttachment(0, 3);
		fd_table.left = new FormAttachment(0, 3);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		String[] titles = { "Start Date", "End Date","Start Time", "End Time", "Project", "Hours Worked"};
        for (int i = 0; i < titles.length; i++) {
          TableColumn column = new TableColumn(table, SWT.NONE);
          column.setText(titles[i]);
        }
		
		populateTable();
		
        for (int i = 0; i < titles.length; i++) {
            table.getColumn(i).pack();
        }
	}
	
	protected void updateTable(){

		table.clearAll();
		table.removeAll();
		populateTable();
	}
	
	private void populateTable() {
		timesheets = accessTimesheet.getCurrentUserTimesheet();
		for(int i = 0; i < timesheets.size(); i++) {
        	Timesheet currTimesheet = timesheets.get(i);
            TableItem item = new TableItem(table, SWT.NONE);
            
            item.setText(0, currTimesheet.getStartDateString());
            item.setText(1, currTimesheet.getEndDateString());
            item.setText(2, currTimesheet.getStartTimeString());
            item.setText(3, currTimesheet.getEndTimeString());
            item.setText(4, currTimesheet.getProjectName());
            item.setText(5, Float.toString(currTimesheet.getHours()));
        }
		
	}
}
