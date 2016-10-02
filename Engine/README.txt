Engine v3.1 
Author:Corey Glasson
Project was written, compiled and executed using:

	Eclipse Standard/SDK

	Version: Luna Service Release 1a (4.4.1)
	Build id: 20150109-0600
	

JRE System Library:
	JavaSE-1.8
	Processing Library v2.2.1


Executable:
GameLoop.java
	(server setup)
	Usage:	GameLoop 
	1. At prompt, enter <server>
	2. server will perform setup and begin listening for connections

	(client connection)
	1. At prompt, enter <client>
	2. At prompt, enter port to open connection  to server on
	3. At prompt, enter player name
	4. At prompt, enter chosen color (out of red, blue, green, yellow)

	server will add client during execution of it's loop and periodically update clients

	*No game setup is implemented right now, only networking, event, and object systems are functioning.*
