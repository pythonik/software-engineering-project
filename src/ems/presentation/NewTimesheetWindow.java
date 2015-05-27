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
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Combo;
import java.util.GregorianCalendar;
import java.awt.Toolkit;
import ems.business.AccessTimesheet;
import ems.business.AccessUser;
import ems.business.AccessProject;
import ems.business.CalculateDate;
import ems.business.CalculateHours;
import ems.models.Timesheet;
import ems.models.Project;

import java.util.ArrayList;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Control;

public class NewTimesheetWindow {

	private Shell shlEmsNew;
	private Display display;
	private EmployeeWindow parentWindow;
	
	private final int BUTTON_PANEL_HEIGHT = 40;
	
	//Containers for GUI components
	private Composite buttonsPanel;
	private Composite viewPanel;
	
	//GUI components
	private Button btnCancel;
	private Button btnSubmit;
	private Combo comboProjectList;
	private Label lblStartTime;
	private Label lblEndTime;
	private Label lblDate;
	private Label lblProject;
	private Label lblEndDateif;
	
	//Timesheet variables
	private GregorianCalendar dateStart;
	private GregorianCalendar dateEnd;
	
	//Data Access
	private AccessTimesheet accessTimesheet;
	private AccessUser accessUser;
	private AccessProject accessProject;
	private ArrayList<Project> projects;
	private DateTime timeStartTime;
	private DateTime timeEndTime;
	private DateTime calStartDate;
	private DateTime calEndDate;
	private Label lblErrorImg;
	private Label lblErrorMsg;	
	private Label label;
	
	public NewTimesheetWindow(AccessUser accessUser, EmployeeWindow parentWindow) {
		this.accessUser = accessUser;
		this.parentWindow = parentWindow;
		accessTimesheet = new AccessTimesheet();
		accessProject = new AccessProject();
		projects = new ArrayList<Project>();
	}
	
	private void updateCalendars(){
		dateStart.clear();
		dateStart.set(
				calStartDate.getYear(), 
				calStartDate.getMonth(), 
				calStartDate.getDay(),
				timeStartTime.getHours(),
				timeStartTime.getMinutes()
		);				
		
		dateEnd.clear();
		dateEnd.set(
				calEndDate.getYear(), 
				calEndDate.getMonth(), 
				calEndDate.getDay(),
				timeEndTime.getHours(),
				timeEndTime.getMinutes()
		);				
	}
	
	private void errorHandler(String errorMsg)	{
		lblErrorMsg.setText(errorMsg);
		
		lblErrorImg.setVisible(true);
		lblErrorMsg.setVisible(true);
		
		lblErrorMsg.redraw();
		lblErrorImg.redraw();
		
		Toolkit.getDefaultToolkit().beep();
	}
	
	private void errorClear() {
				
		lblErrorImg.setVisible(false);
		lblErrorMsg.setVisible(false);
		
		lblErrorMsg.redraw();
		lblErrorImg.redraw();
	}

	public void open() {
		display = Display.getDefault();
		createContents();
		shlEmsNew.open();
		shlEmsNew.layout();
		
		dateStart = new GregorianCalendar();
		dateEnd = new GregorianCalendar();
		
		while (!shlEmsNew.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}			
		}
		shlEmsNew.dispose();
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
		shlEmsNew = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.MIN);
		shlEmsNew.setSize(353, 270);
		shlEmsNew.setText("EMS - New Timesheet");
		shlEmsNew.setLayout(new FormLayout());
		shlEmsNew.setImage(SWTResourceManager.getImage(NewTimesheetWindow.class, "/resources/EMSLogo.jpg"));
	}
	
	private void createPanels() {
		buttonsPanel = new Composite(shlEmsNew, SWT.NONE);
		buttonsPanel.setLayout(null);

		FormData fd_buttonsPanel = new FormData();
		fd_buttonsPanel.bottom = new FormAttachment(100);
		fd_buttonsPanel.left = new FormAttachment(0);
		buttonsPanel.setLayoutData(fd_buttonsPanel);		
		
		//Create view panel
		viewPanel = new Composite(shlEmsNew, SWT.NONE);
		fd_buttonsPanel.right = new FormAttachment(viewPanel, 0, SWT.RIGHT);
		fd_buttonsPanel.top = new FormAttachment(viewPanel, 2);
		
		FormData fd_viewPanel = new FormData(); 
		fd_viewPanel.left = new FormAttachment(0);
		fd_viewPanel.right = new FormAttachment(100);
		fd_viewPanel.top = new FormAttachment(0);
		fd_viewPanel.bottom = new FormAttachment(100, -42);
		viewPanel.setLayoutData(fd_viewPanel);
	}
	
	private void createComponents() {
		createLabels();
		createCombos();
		createButtons();
		createDateTimes();
	}
	
	private void createLabels() {
		lblStartTime = new Label(viewPanel, SWT.NONE);
		lblStartTime.setBounds(255, 10, 57, 15);
		lblStartTime.setText("Start Time:");
		
		lblEndTime = new Label(viewPanel, SWT.NONE);
		lblEndTime.setBounds(255, 61, 53, 15);
		lblEndTime.setText("End Time:");
		
		lblDate = new Label(viewPanel, SWT.NONE);
		lblDate.setAlignment(SWT.RIGHT);
		lblDate.setBounds(140, 10, 55, 15);
		lblDate.setText("Start Date:");
		
		lblProject = new Label(viewPanel, SWT.NONE);
		lblProject.setBounds(10, 139, 40, 15);
		lblProject.setText("Project:");
		
		lblEndDateif = new Label(viewPanel, SWT.NONE);
		lblEndDateif.setBounds(136, 61, 50, 15);
		lblEndDateif.setText("End Date:");
		
		lblErrorImg = new Label(viewPanel, SWT.NONE);
		lblErrorImg.setImage(SWTResourceManager.getImage(NewTimesheetWindow.class, "/com/sun/java/swing/plaf/windows/icons/Error.gif"));
		lblErrorImg.setBounds(169, 139, 31, 32);
		lblErrorImg.setVisible(false);
		
		lblErrorMsg = new Label(viewPanel, SWT.WRAP);
		lblErrorMsg.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblErrorMsg.setBounds(206, 139, 131, 54);
		lblErrorMsg.setVisible(false);
	}
	
	private void createButtons() {
		btnCancel = new Button(buttonsPanel, SWT.NONE);
		btnCancel.setBounds(10, 3, 75, BUTTON_PANEL_HEIGHT - 8);
		btnCancel.addSelectionListener(new SelectionAdapter() { 
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shlEmsNew.close();
			}
		});
		btnCancel.setText("Cancel");
		
		
		btnSubmit = new Button(buttonsPanel, SWT.NONE);
		
		btnSubmit.setBounds(262, 3, 75, 32);
		btnSubmit.addSelectionListener(new SelectionAdapter() { 
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (!((CalculateDate.timesheetDateInBounds(dateStart)) && (CalculateDate.timesheetDateInBounds(dateEnd)))){					
					errorHandler("Timesheets cannot be filled out after more than seven days, and cannot be filled in advance.");					
				}
				else if(dateStart.equals(dateEnd)) {					
					errorHandler("Cannot submit timesheet of zero length");					
				}
				else if(dateStart.after(dateEnd)) { 				
					//Error is already handled elsewhere. This just ensures that no timesheet is submitted.
				}
				else{					
					Timesheet newSheet = new Timesheet(
							accessUser.getCurrentUserName(),
							accessUser.getCurrentUserID(),
							projects.get(comboProjectList.getSelectionIndex()),
							dateStart,
							dateEnd);
					newSheet.setHours(CalculateHours.getDifferenceInHours(dateStart, dateEnd));
					accessTimesheet.insertTimesheet(newSheet);
					parentWindow.redraw();
					parentWindow.updateTable();
					shlEmsNew.close();
				}
			}
		});
		
		btnSubmit.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				errorClear();
			}
		});
		btnSubmit.setText("Submit");
	}
	
	private void createCombos() {
		comboProjectList = new Combo(viewPanel, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboProjectList.setBounds(10, 160, 142, 23);
		projects = accessProject.getAllProjects();
		for(int i = 0; i< projects.size(); i++){
			comboProjectList.add(projects.get(i).getName());
		}
		comboProjectList.select(0);	

	}
	
	private void createDateTimes() {
		timeStartTime = new DateTime(viewPanel, SWT.BORDER | SWT.DROP_DOWN | SWT.TIME | SWT.SHORT);
		
		timeStartTime.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				updateCalendars();
				errorClear();
				
				if(dateStart.after(dateEnd)){
					timeStartTime.setTime(timeEndTime.getHours(), timeEndTime.getMinutes(), timeEndTime.getSeconds());
					timeStartTime.layout();					
					errorHandler("Action Blocked. Cannot set start time after end time.");
				}				
			}
		});	
		
		timeStartTime.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				errorClear();
			}
		});
		timeStartTime.setBounds(255, 31, 82, 24);
		
		
		timeEndTime = new DateTime(viewPanel, SWT.BORDER | SWT.TIME | SWT.SHORT);
		
		timeEndTime.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				updateCalendars();
				errorClear();
				
				if(dateStart.after(dateEnd)){
					timeEndTime.setTime(timeStartTime.getHours(), timeStartTime.getMinutes(), timeStartTime.getSeconds());
					timeEndTime.layout();
										
					errorHandler("Action Blocked. Cannot set end time before start time.");
				}				
			}
		});		
		timeEndTime.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				errorClear();
			}
		});
		
		timeEndTime.setBounds(255, 82, 82, 24);
		
		
		calStartDate = new DateTime(viewPanel, SWT.BORDER);
		
		calStartDate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				updateCalendars();
				errorClear();
				
				if(dateStart.after(dateEnd)){
					calStartDate.setDate(calEndDate.getYear(), calEndDate.getMonth(), calEndDate.getDay());
					calStartDate.layout();
					
					errorHandler("Action Blocked. Cannot set start date after end date.");
				}				
			}
		});		
		calStartDate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				errorClear();
			}
		});
		calStartDate.setBounds(140, 31, 80, 24);
		
		
		calEndDate = new DateTime(viewPanel, SWT.BORDER);
		
		calEndDate.addSelectionListener(new SelectionAdapter() {
			@Override			
			public void widgetSelected(SelectionEvent arg0) {				
				updateCalendars();
				errorClear();
				
				if(dateStart.after(dateEnd)){
					calEndDate.setDate(calStartDate.getYear(), calStartDate.getMonth(), calStartDate.getDay());
					calEndDate.layout();
					
					errorHandler("Action Blocked. Cannot set end date before start date.");
				}				
			}
		});
		calEndDate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0){
				errorClear();					
			}
		});
		calEndDate.setBounds(140, 82, 80, 24);
		
		label = new Label(viewPanel, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(NewTimesheetWindow.class, "/resources/EMSLogo.jpg"));
		label.setBounds(10, 10, 80, 80);
		viewPanel.setTabList(new Control[]{calStartDate, timeStartTime, timeEndTime, calEndDate, comboProjectList});
	}
}
