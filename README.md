# Employee Management App
An Android app for managing employees, their name and holiday requests, and notifications, designed for admins and employees.

# Features
- Login system for admins and employees
- View and manage employee details
- Name change and holiday request approvals/rejections by admins
- Notifications for employees about request updates
- SQLite database for local data storage
- Clean and intuitive user interface

- # Technologies Used
- Java
- SQLite for local database
- API
- Android Studio
- RecyclerView for displaying lists
- SharedPreferences for user session management

- ## Design Patterns Implemented
- **MVC (Model-View-Controller)**: For separating concerns in the app.
- **Adapter Pattern**: Used in RecyclerViews for notifications and requests.
- **Singleton Pattern**: Ensures a single instance of the database helper class.

- ## How to Use
1. **Login**: Admins and employees can log in with their credentials.
2. **Admin Dashboard**:
   - View employee details.
   - Approve/reject name change and holiday requests.
   - Notifications are automatically sent to employees after admin action.
3. **Employee Features**:
   - View details.
   - Submit name change and holiday requests.
   - View notifications for updates.

## Database Schema
- **Employees Table**:
  - Columns: `id`, `firstname`, `lastname`, `email`, `department`, `joiningdate`, `leaves`, `salary`
- **Users Table**:
  - Columns: `id`, `email`, `password`, `isAdmin`
- **Notifications Table**:
  - Columns: `id`, `title`, `message`, `recipientEmail`, `timestamp`
- **Holiday Requests Table**:
  - Columns: `id`, `email`, `startDate`, `endDate`, `totalDays`, `status`
- **Name Change Requests Table**:
  - Columns: `id`, `email`, `newFirstName`, `newLastName`, `status`



