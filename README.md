# csci201_resonate
CSCI 201 Final Group Project: Resonate
Group Members: Daniel Koo, Isaac Taylor, Jerry Tejada, Tori Yi

## Structure

- Resonate
	- Java Resources
		- src : Servlets
	- WebContent : main jsp files
		- images : Photos used in jsps
		- includes : Resources used by jsps
			- js : Javascript libraries and page scripts
			- css : stylesheets for each page
		- uploads : where user-uploaded music tracks will go

## Works
Able to sign up/log off Guest versus Signed Up member difference (guests can only browse projects they cannot create projects) Users can edit profile Live count works Can add and select tracks Can create and choose a project Mostly everything else works :D

## Not Working/Scrapped (from Documentation)
We ended up not implementing roles Voting/Liking Projects is the same, there is no UnLiking a project We can't delete tracks Can't share a project or delete a project

## Additional Deployment notes
ffmpeg is not a nice "write once, run anywhere" java thing. If you're on mac, make sure
you the pathToProject in  csci201_resonate/Resonate/src/com/resonate/Config.java to 
wherever you saved the project! This will allow the creation and downloading of compiled
tracks to work. If you're not on a mac, sorry. The program is there but we messed up
the code. We'll fix it I promise.
