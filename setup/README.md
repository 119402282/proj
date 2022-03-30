
Hi Michael,

Our database is embedded and should not require any changes 
apart from the inclusion of the derby library if not already included.
The data is prepopulated and has a complete schema.
It is ready to run!!

Here are credentials for each tier of user:
Patient:	7482091L
Password:	ILikeGoats2367

Clinician:	lcotter546@hse.ie
Password:	PotsAndPans12

PHA:		jclint897@hse.ie
Password:	ExcellentElegance	

*Note: please make sure you are disconnected from the database and also Stop running its Java DB server while running the program
The database has been embedded and doing so may cause errors in the netbeans environment however works fine in DataGrip and IntelliJ and eclipse. 

HOWEVER
In the event that you do wish to setup the database on your locahost please do the following:
	To setup the database please create a database in the project with the following details.
	Name: IS2209CA2DB
	User: root
	Password: pass

	Then please run the setup.sql stored in the "setup" directory to build schema and populate with data
	
	Finally navigate to the code and change the DB_URL in the DataAccess.java file to 'jdbc:derby://localhost:1527/IS2209CA2DB'

	**You should not have to disconnect from the db or stop the server for it to run**

Now the project should be ready to build and run

