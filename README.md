# MaKore
## Best Way to Communicate

MaKore is a communication app we build during the semester<br />
This is the third part - Android application.<br />
In addition to the previous parts, we used and experienced with: Java, Android, activities and layouts, Firebase and Room.<br />

### About the Application
The application is another client side for our MaKore chat.
Of course the app also communicates with the server from the last part, and gets information from it.<br />

### Signin and Register
These pages stay the same. <br />
![signinSignup](https://user-images.githubusercontent.com/90967892/174582296-f2469f78-1121-40d0-81d3-56c54f04f1f7.jpg)
 <br />
A new user can add a photo from the camera directly or from his gallery. <br />
Of course all kinds of varifications still exist. <br />
![varificationPhoto](https://user-images.githubusercontent.com/90967892/174582595-725eb737-4c9b-4aae-959b-8fc297349439.jpg)
 <br />

### Chats
A logged in or new registered user will be directly moved to his chats. <br />
The chat page has changed since now it is seperated from the conversations (at least in portrait mode).<br />
![newUserContact](https://user-images.githubusercontent.com/90967892/174583212-3bc38d7b-f341-44d8-b460-44b1619c25d0.jpg)
<br />
Searching users in the chat is still available. The search is case-sensitive. <br />
<br />
![search](https://user-images.githubusercontent.com/90967892/174586293-abb63d56-a540-4cbe-bcbe-9028684fc143.jpg)
<br />

### Single Chat - Conversation
When clicking on a user, we move to the conversation page. <br />
There we can send messages to the chosen friend. <br />
<br />
![msg](https://user-images.githubusercontent.com/90967892/174583701-d4579de7-9d26-43ab-8f75-cf475a90f775.jpg)
<br />

### Settings
The settings is the place for the user to adjust some things in the app. <br />
A user can change the theme of the application from the original purple theme of MaKore to a new yellow theme ! <br />
The user can also change his deafult server to be connected to another one. <br />
Finally, the user can log out from the application and be redirected to the Login page. <br />
![settings](https://user-images.githubusercontent.com/90967892/174584172-e0598d9c-947d-427e-938a-cd7b302b2c33.jpg)
<br />

#### The Yellow Theme
When a user chooses the yellow theme his application will turn yellow and orange ! <br />
If the user exits the app (log out or just close the app) his theme preferences will be kept for him. <br />
![theme](https://user-images.githubusercontent.com/90967892/174584502-f4d1a6e1-5e4d-41e8-92e2-4be5c533b209.jpg)
<br />

### Landscape Mode
Our application supports an innovative landscape mode for the user's convience !<br />
![landChats](https://user-images.githubusercontent.com/90967892/174584626-01420cdb-ee2a-4074-95a1-23f70c342ccc.jpg)
<br />
As you can see, this mode allows the user see all of his chats with friends, add a new friend, talk to a friend and move to the settings - all in one page. <br />

### Talking to Other Clients of MaKore
As we said before, this client-side application communicates with our web client. <br />
Let's take a look on Ido who logged in to the MaKore app. <br />
Tal on the other hand, logged in to the MaKore website. <br />
They can easily communicate on real time. When Tal sends Ido a message, Ido receives it at his app and replies. <br />
![receivesMsg](https://user-images.githubusercontent.com/90967892/174585481-065db97c-bebf-4dc6-af4d-3c02e8a1b670.jpg)
![receivesMsg2](https://user-images.githubusercontent.com/90967892/174585575-91b9539e-724f-4d60-a74e-7b47c6dcb9fc.jpg)

### Notifications
When a user gets a message a notification will pop: <br />
![notification](https://user-images.githubusercontent.com/90967892/174586101-9ef366f0-aab9-4d24-8c69-63e3be6f1a18.jpg)
<br />

#### Notes:
- We only support text messages now.
- All the requirments of signing up to MaKore stay the same.
- There is one picture (of MaKore) for all users.
- We assume that "bearer JWT" and "@microsoft/signalR" packages are already installed on your PC.
- Please make sure the server is running on "localhost:5018" and the client is running on "localhost:3000".
- **Please do not store or send important information like real passwords or important text on the server because this application does not take into consideration many considerations that must be taken in the context of the security of the application.**

### How to Run
Our project divides into two servers, and thus two repositories. <br />
The first repository is MaKore - the first assigment. There we branched to "ex2" and left "master" intact for those who check our first assigment. (A link to that repository is also in details.txt). <br />
As in for this repository, here is the server. You need to run it first in order to test our chat itself. Also, the homepage (localhost) of the server is the rating page, although you can easily get there using the button on the chat itself (as explained before). <br />
<br />
1. Clone this repository - the server:
    ```
    git clone https://github.com/CoralKuta/MaKoreChat
    ```
2. Enter the MaKore folder and open the project:
    ```
    cd MaKoreChat/MaKore.sln
    ```
3. Open the Package Manager Console and update the database (that you already cloned)
    ```
    update-database
    ```
4. run the server code (from the MaKore.sln)
    ```
    Ctrl+F5
    ```
5. Clone the Client repository:
    ```
    git clone -b ex2 https://github.com/CoralKuta/MaKore
    ```
6. Enter the MaKore client folder:
    ```
    cd MaKore
    ```
7. Install all modules:
    ```
    npm install
    ```
8. Run the program:
    ```
    npm start
    ```
9. Sign up and Explore!

### Developers
- Ido Tavron 316222512
- Coral Kuta 208649186
- Tal Dabran 316040898

