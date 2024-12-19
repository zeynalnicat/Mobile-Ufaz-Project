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

### Details Screen
- View detailed information about a selected track or album.
- Play a short preview of a track using the provided button.

### Playlist Screen
- **Playlist Management:** Create, view, and manage personalized playlists.
- **Track Organization:** Add tracks to playlists to curate a customized music experience.

### Quiz Screen
- **Quiz Management:**
  - Create and manage quizzes linked to playlists.
  - View quiz details, including associated playlist and quiz name.
  - Start quizzes with a single button.
- **Quiz Gameplay:**
  - Randomly generated questions.
  - Game modes:
    - **Multiple Choice:** Select the correct answer from a list.
    - **Open-Ended:** Enter the correct answer manually.
  - Play track previews for each question.

### Local Database
- Use Room for storing playlists and quizzes locally to ensure data persistence.

#### Screenshots
![Search Screen](path/to/search_screen_screenshot.png)
![Playlist Screen](path/to/playlist_screen_screenshot.png)
![Quiz Screen](path/to/quiz_screen_screenshot.png)

---

## Technical Section

### App Architecture
MusicApp is built using the **MVVM (Model-View-ViewModel)** architecture for efficient data handling and separation of concerns.

#### Architecture Diagram
```plaintext
+----------------+       +----------------+       +----------------+
|     View       |  <--  |   ViewModel    |  -->  |     Model      |
| (Fragments, UI)|       |  (LiveData,    |       | (Room, Deezer  |
|                |       |  Repository)   |       | API Access)    |
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
