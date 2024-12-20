# MusicalQuiz - Music Exploration and Quiz

## Overview
MusicApp is a feature-rich mobile application designed to make music discovery and interaction engaging and fun. Using the Deezer API, the app offers functionalities such as searching for music, creating playlists, and participating in quizzes. Below is the detailed report of our project.

---

## Functionalities
MusicApp provides the following features:

### Search Screen
- **Music Search:** Search for tracks or albums using a simple search bar.
- **Interactive Results:** Display search results in a grid layout showing album covers, titles, and artist names.
- **Actions on Results:**
  - Click to view detailed information.
  - Click options to add the item to the desired playlist ( you can choose which playlist you want to add).
  <img width="362" alt="image" src="https://github.com/user-attachments/assets/0143a4c5-5a30-41fd-82e8-6d28ab91153e" />

### Details Screen
- View detailed information about a selected track or album.
- Play a short preview of a track using the provided button.
  <img width="366" alt="image (1)" src="https://github.com/user-attachments/assets/660e2369-dd1e-4ea7-87df-d218826c78c5" />
  <img width="365" alt="image (5)" src="https://github.com/user-attachments/assets/254d263a-5199-4eb8-b120-e2ade4d91642" />



### Playlist Screen
- **Playlist Management:** Create, view, and manage personalized playlists.
- **Track Organization:** Add tracks to playlists to curate a customized music experience.
  <img width="368" alt="image (4)" src="https://github.com/user-attachments/assets/a40521ed-9c11-4ae9-a9bf-f046bafb9525" />


### Quiz Screen
- **Quiz Management:**
  - Create and manage quizzes linked to playlists.
  - View quiz details, including associated playlist and quiz name.
  - Start quizzes with a single button.
    <img width="364" alt="image (3)" src="https://github.com/user-attachments/assets/17d9794b-ec49-412d-b0f8-1fa2ea45207a" />

- **Quiz Gameplay:**
  - Randomly generated questions.
  - Game modes:
    - **Multiple Choice:** Select the correct answer from a list.
    - **Open-Ended:** Enter the correct answer manually.
  - Play track previews for each question.
    <img width="370" alt="image (2)" src="https://github.com/user-attachments/assets/3524e983-1679-425d-82d4-c66f79c73433" />


### Local Database
- Use Room for storing playlists and quizzes locally to ensure data persistence.
  <img width="685" alt="image (6)" src="https://github.com/user-attachments/assets/b224d24c-68ce-4d81-9ff6-effd38ed4c05" />


---

## Technical Section

### App Architecture
MusicApp is built using the **MVVM (Model-View-ViewModel)** architecture for efficient data handling and separation of concerns.

#### Architecture Diagram
```plaintext
+----------------+       +----------------+       +----------------+
|     View       |  <--  |   ViewModel    |  -->  |     Model      |
| (Fragments, UI)|       |  (LiveData,)   |       | (Room, Deezer  |
|                |       |                |       | API Access)    |
+----------------+       +----------------+       +----------------+
```

#### Implementation Choices
1. **Bottom Navigation Bar:** Enables seamless navigation between fragments (Search, Details, Playlist, and Quiz).
2. **RecyclerView:** Used for displaying search results and playlists for efficient and scalable lists.
3. **Room Database:** Provides robust local storage for playlists and quizzes, ensuring offline access.
4. **ViewModel:** Handles data and manages configuration changes for a smooth user experience.

---

## Technical Problems
While the project is functional, a few technical challenges remain unresolved:
1. **Offline Mode:** The caching system for offline access is not implemented.


---

## Contributions

### Team Members
- **Polad Ibrahimov:** Focused on Search, Details, and Playlist screens, including integration with the Deezer API.
- **Nijat Zeynalli:** Developed the Quiz screen, local database (Room) setup, and ensured adherence to the MVVM architecture.
