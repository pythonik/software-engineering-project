	==================================
	=EMS - Employee Management System=
	==================================

		Coded by -
		
		Dylan Blass-Svedvik
		Evan Burgess
		Dale Hildebrandt
		Ryan Klippenstine
		Kun Su
		
		
		Sections:
		
		1. Compiling and Set-up
			1a. Restoring the database
		2. Testing
		3. Executing
			3a. Employee Window
				-New Timesheet window
				-Details window
			3b. Manager Window
				-Employee Details window
			3c. Financial Window
				-Title Summary window
				-Pay Stub window
		4. Code Packages
			4a. application
			4b. business
			4c. models
			4d. persistence
			4e. presentation
			4f. test packages
			4g. resources
		5. Change Log
			5a. Iteration 2
		
		
	==============
	=1. Compiling=
	==============

To compile the project, run the included Compile.bat file from the project directory. 
The source files will be compiled, and the class objects added to the "bin" directory.

	1a.
If something goes wrong with the database or the database just needs to be cleared, run the included RestoreDB.bat file.
The database will be cleared of all new data and returned to its initial state with the original set of data.


	============
	=2. Testing=
	============

To test the class objects created by Compile.bat, run the included RunUnitTest.bat file from the project directory.
This will run all of the included unit tests (using the AllTests class), which should result in 70/70 successful runs.


	==============
	=3. Executing=
	==============

To run the program, run the included Run.bat file from the project directory. The project's log-in screen will appear.
The project has two primary views - the manager view and the employee view. Which view the user sees depends on which
employee is selected from the log-in screen. At present, Smith, A (the default option) is the only manager, and every
other name is an employee. Once a name is selected, press the Log In button (no password necessary right now) and the
corresponding window will appear.

	3a.
If a regular employee is selected, the employee view will be opened. The employee's name will appear on the top left.
In the middle of a screen, there is a table with all of the employee's previous timesheet entries. This will display
the Date, Start Time, End Time, Project Name, and Hours Worked for that entry. On the bottom left, the "Quit" button
will exit the program. The "Log Out" button will return the user to the log-in screen. The "New" button on the bottom-
right will allow the user to enter a new timesheet, and the "Details" button will show employee details - specifically,
how many hours the employee has worked on each project.

The New button brings up a new window used to enter timesheet information. While this window is active, the main Employee
window will be disabled. The calendar on the top-left is used to denote the start date; there is a box on the middle-right
to set the end date. The boxes on the top right are used to set the start time and end time, respectively (though the
minutes are presently ignored). The "project" drop-down on the bottom is used to select which project the time was spent
on. The cancel button will return to the employee view, while the submit button will send the entered information to
the database before returning to the employee view. On submit, the table on the employee view will be refreshed to reflect
the new entry.

The Details button will bring up a page displaying the employee's details. It will show their name, as well as how many
hours they have worked on each project. The Close button will return to the employee view.

	3b.
If a manager is selected from the log-in screen, the manager view will be opened. The manager view is similar to the
employee view, only the manager view allows the user to view the timesheets for all of the employees in their management
group. The Quit and Log Out buttons work as they did in the employee view. On the top-right, there is a drop-down box used
to change which employee's timesheets are displayed on the table. 

The Details button will bring up the Employee Details window for the employee selected. This will show how many hours the
selected employee has worked on each project, the same as it did in the employee view.

	3c.
If a financial user is selected from the log-in screen, the financial view will be opened. The financial view shows the
financial summary of other users within the current pay period, including how many other users exist and how much they
have earned in total. The Per-Title Summary button displays a window showing data on individual titles for the current
pay period. The financial user may also select an employee in the combo box and generate their pay stub by selecting
"view paystub." The Quit and Log Out buttons work as they did in the other user windows.

The Per-Title Summary button displays a window which shows the total hours and total amount earned, grouped by title, 
by employees during the last pay period.

The View Paystub button displays a window showing financial data for the selected user over the last pay period. It
displays the employee's name, the pay period, the hours worked by the employee, the employee's wage, and the total
amount earned by the employee; this information represents a generated paystub. The "Close" button closes the Paystub
window and returns to the financial view. The "Print" button is disabled and unimplemented; it is simply there to show
intended functionality, which is to automatically generate and then print a paystub for the financial users.


	==================
	=4. Code Packages=
	==================

	4a. application
The application package is a very simple package. It contains only the "Main" class; this class is used to set up and
create the rest of the application.

	4b. business
The business package is used primarily by the presentation package (and other classes in the business package) to
perform various tasks. Any calculations are handled by the business package. The business package is also used to serve
data to the presentation layer and, where needed, format this data to suit the presentation package's needs.

	4c. models
The models package holds the data types used in the application. Projects, timesheets, users, etc. When the rest of the
application passes data back and forth, the data is primarily stored in one of the model classes.

	4d. persistence
The persistence package is where the data is stored and accessed. It holds the class that interfaces between the 
application and the database. It also holds the classes that serve this data to the business package.

	4e. presentation
The presentation package is where all of the GUI classes are stored. When the application is run, these classes
define what the user will see, and how they interact with the program data and the other packages. The package is primarily
used to present data to the user, and allows them to manipulate that data.

	4f. test packages
The test packages store the unit tests for the various other packages in the project. The tests package itself stores the
class responsible for running all of the other tests. The tests.business package holds the test for the business package,
testing the various calculations and other functions in the business package. The tests.models package holds tests for the
models, ensuring that the data each model deals with is being stored and retrieved correctly. The tests.stub package
ensures that the stub database is being constructed correctly, and tests to make sure that the data held in the stub can
is being accessed appropriately.

	4g. resources
The resources package holds the non-code resources used by the application - in other words, the EMS logo. This ensures
that the image is not lost in compilation.


	============
	=5. Changes=
	============
	
	5a. Iteration 2
-Moved the main application from the stub database to the real database.
-Added the Financial view to the application.
	-Added the ability for financial users to generate pay stubs.
-Broke the "Calculation" class into several more specialized classes: CalculateDate, CalculateHours, and FinancialCalculator.
-Changed the "New Timsheet" window; both the start date and the end date are now text boxes. 