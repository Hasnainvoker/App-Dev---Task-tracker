package com.example.labpro;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "check5.db";
    private static final int DATABASE_VERSION = 1;

    private static DBHelper instance;

    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        db.execSQL("CREATE TABLE IF NOT EXISTS Users (" +
                "userId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "email TEXT UNIQUE," +
                "password TEXT," +
                "phone_number TEXT" +
                ")");

        // Create Categories table
        db.execSQL("CREATE TABLE IF NOT EXISTS Categories (" +
                "categoryID INTEGER PRIMARY KEY," +
                "categoryName TEXT" +
                ")");

        // Insert common categories
        insertCategory(db, 1, "Health");
        insertCategory(db, 2, "Hygiene");
        insertCategory(db, 3, "Productivity");
        insertCategory(db, 4, "Sports");

        // Create CommonHabits table
        db.execSQL("CREATE TABLE IF NOT EXISTS CommonHabits (" +
                "habitID INTEGER PRIMARY KEY," +
                "categoryID INTEGER," +
                "habitName TEXT," +
                "question TEXT," +
                "frequency INTEGER," +  // Change the data type to INTEGER
                "notes TEXT," +
                "reminder INTEGER," +
                "FOREIGN KEY (categoryID) REFERENCES Categories(categoryID)" +
                ")");
        // Insert common habits with reminder set to yes
        // Health category
        // Health category
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (1, 1, 'Checkup', 'Did you get a checkup?', 1, 'Regular health checkups are important for preventive care.', 1)");
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (2, 1, 'Walking', 'Did you go for a walk?', 2, 'Walking is a good exercise for overall health.', 1)");
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (3, 1, 'Running', 'Did you go for a run?', 3, 'Running helps improve cardiovascular health.', 1)");
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (4, 1, 'Meals', 'Did you have your meals?', 3, 'Maintain a balanced diet with regular meals.', 1)");
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (5, 1, 'Gym', 'Did you visit the gym?', 5, 'Regular gym sessions help in maintaining fitness.', 1)");

// Hygiene category
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (6, 2, 'Brushing', 'Did you brush your teeth?', 2, 'Brushing helps maintain oral hygiene.', 1)");
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (7, 2, 'Shower', 'Did you take a shower?', 1, 'Regular showers are essential for personal hygiene.', 1)");
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (8, 2, 'Shaving', 'Did you shave?', 2, 'Shaving helps maintain a groomed appearance.', 1)");
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (9, 2, 'Trim Nails', 'Did you trim your nails?', 1, 'Trimmed nails prevent dirt buildup and infections.', 1)");
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (10, 2, 'Change Sheets', 'Did you change your sheets?', 1, 'Regularly changing sheets promotes better sleep and hygiene.', 1)");

// Productivity category
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (11, 3, 'Working', 'Did you work today?', 8, 'Balancing work and leisure is important for productivity.', 1)");
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (12, 3, 'Reading Books', 'Did you read a book today?', 2, 'Reading books enhances knowledge and improves vocabulary.', 1)");
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (13, 3, 'Writing', 'Did you write today?', 1, 'Writing helps in expressing thoughts and ideas.', 1)");
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (14, 3, 'Studying', 'Did you study today?', 2, 'Regular studying aids in academic success.', 1)");
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (15, 3, 'Coding', 'Did you code today?', 3, 'Coding skills are essential in the digital age.', 1)");

// Sports category
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (16, 4, 'Cricket', 'Did you play cricket?', 2, 'Playing cricket improves teamwork and physical fitness.', 1)");
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (17, 4, 'Football', 'Did you play football?', 1, 'Football is a popular team sport.', 1)");
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (18, 4, 'Tennis', 'Did you play tennis?', 2, 'Playing tennis improves agility and coordination.', 1)");
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (19, 4, 'Table Tennis', 'Did you play table tennis?', 2, 'Table tennis improves reflexes and hand-eye coordination.', 1)");
        db.execSQL("INSERT INTO CommonHabits (habitID, categoryID, habitName, question, frequency, notes, reminder) VALUES (20, 4, 'Basketball', 'Did you play basketball?', 1, 'Basketball is a high-intensity sport.', 1)");

        // Create CustomHabits table
        db.execSQL("CREATE TABLE IF NOT EXISTS CustomHabits (" +
                "habitID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "habitName TEXT," +
                "question TEXT," +
                "frequency TEXT," +
                "notes TEXT," +
                "reminder INTEGER" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS Notify (" +
                "notifyID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER," +
                "time TEXT," +
                "FOREIGN KEY (userId) REFERENCES Users(userId)" +
                ")");

        // Create LogHabits table
        db.execSQL("CREATE TABLE IF NOT EXISTS LogHabits (" +
                "logID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER," +
                "habitID INTEGER," +
                "time TEXT," +
                "status INTEGER," + // Change data type to INTEGER
                "FOREIGN KEY (userId) REFERENCES Users(userId)," +
                "FOREIGN KEY (habitID) REFERENCES CommonHabits(habitID)" +
                ")");

        // Create UserHabits table
        db.execSQL("CREATE TABLE IF NOT EXISTS UserHabits (" +
                "userHabitID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER," +
                "commonHabitID INTEGER," +
                "customHabitID INTEGER," +
                "FOREIGN KEY (userId) REFERENCES Users(userId)," +
                "FOREIGN KEY (commonHabitID) REFERENCES CommonHabits(habitID)," +
                "FOREIGN KEY (customHabitID) REFERENCES CustomHabits(habitID)" +
                ")");

        // Create CommunityChallenges table
        db.execSQL("CREATE TABLE IF NOT EXISTS CommunityChallenges (" +
                "challengeID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "challengeName TEXT," +
                "description TEXT," +
                "date TEXT," +
                "place TEXT" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS user_community_challenges (" +
                "userId INTEGER," +
                "challengeID INTEGER," +
                "completionStatus BOOLEAN," +
                "points INTEGER,"+
                "FOREIGN KEY (challengeID) REFERENCES CommunityChallenges(challengeID)"+
                ")");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Backup data from existing tables if needed

        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Categories");
        db.execSQL("DROP TABLE IF EXISTS CommonHabits");
        db.execSQL("DROP TABLE IF EXISTS CustomHabits");
        db.execSQL("DROP TABLE IF EXISTS UserHabits");
        db.execSQL("DROP TABLE IF EXISTS CommunityChallenges");
        db.execSQL("DROP TABLE IF EXISTS user_community_challenges");
        db.execSQL("DROP TABLE IF EXISTS Notify"); // Include the Notify table
        db.execSQL("DROP TABLE IF EXISTS LogHabits"); // Include the LogHabits table

        // Recreate tables
        onCreate(db);

        // Restore data from backup or perform data migration as needed
    }



    // Modify the DBHelper class to include a method to retrieve log entries as HashMap
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> getLogEntries(int userId) {
        ArrayList<HashMap<String, String>> logEntries = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT time, status FROM LogHabits WHERE userId = ?", new String[]{String.valueOf(userId)});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                HashMap<String, String> entry = new HashMap<>();
                entry.put("time", cursor.getString(cursor.getColumnIndex("time")));
                entry.put("status", String.valueOf(cursor.getInt(cursor.getColumnIndex("status"))));
                logEntries.add(entry);
            }
            cursor.close();
        }

        db.close();
        return logEntries;
    }

    private void insertCategory(SQLiteDatabase db, int categoryID, String categoryName) {
        ContentValues values = new ContentValues();
        values.put("categoryID", categoryID);
        values.put("categoryName", categoryName);
        db.insert("Categories", null, values);
    }

    public ArrayList<String> getUserActivities(int userId) {
        ArrayList<String> activitiesList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        // Fetch common habits associated with the user ID
        Cursor cursor = db.rawQuery("SELECT CH.habitName " +
                "FROM UserHabits AS UH " +
                "INNER JOIN CommonHabits AS CH ON UH.commonHabitID = CH.habitID " +
                "WHERE UH.userId = ? " +
                "UNION " +
                "SELECT CH2.habitName " +
                "FROM UserHabits AS UH2 " +
                "INNER JOIN CustomHabits AS CH2 ON UH2.customHabitID = CH2.habitID " +
                "WHERE UH2.userId = ?", new String[]{String.valueOf(userId), String.valueOf(userId)});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String habitName = cursor.getString(cursor.getColumnIndex("habitName"));
                activitiesList.add(habitName);
            }
            cursor.close();
        }

        db.close();
        return activitiesList;
    }
    public ArrayList<String> getAllUsersInfo() {
        ArrayList<String> usersInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        // Query to retrieve all users' info from the Users table
        Cursor cursor = db.rawQuery("SELECT * FROM Users", null);

        // Iterate through the cursor to extract users' info
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex("userId"));
                @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
                @SuppressLint("Range")  String phoneNumber = cursor.getString(cursor.getColumnIndex("phone_number"));

                // Construct a string with users' info
                String userInfo = "User ID: " + userId + "\n Username: " + username +
                        "\n Email: " + email + "\n Phone Number: " + phoneNumber;

                // Add the user's info string to the list
                usersInfo.add(userInfo);
            }
            cursor.close();
        }

        db.close();
        return usersInfo;
    }
    public long insertNotification(int userId, String time) {
        SQLiteDatabase db = getWritableDatabase();

        // Check if a notification entry already exists for the user
        Cursor cursor = db.rawQuery("SELECT notifyID FROM Notify WHERE userId = ?", new String[]{String.valueOf(userId)});
        long result;

        if (cursor != null && cursor.getCount() > 0) {
            // If an entry exists, update the existing entry
            ContentValues values = new ContentValues();
            values.put("time", time);
            result = db.update("Notify", values, "userId = ?", new String[]{String.valueOf(userId)});
        } else {
            // If no entry exists, insert a new one
            ContentValues values = new ContentValues();
            values.put("userId", userId);
            values.put("time", time);
            result = db.insert("Notify", null, values);
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return result;
    }
    public long insertLogEntry(int userId, int habitId, int status, String time) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("habitID", habitId);
        values.put("status", status);
        values.put("time", time);
        long result = db.insert("LogHabits", null, values);
        db.close();
        return result;
    }

    @SuppressLint("Range")
    public String[] getHabitInfo(String habitName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] habitInfo = null;

        // Check if the habit is present in CommonHabits table
        Cursor cursor = db.rawQuery("SELECT habitID, question FROM CommonHabits WHERE habitName=?", new String[]{habitName});
        if (cursor.moveToFirst()) {
            habitInfo = new String[2];
            habitInfo[0] = String.valueOf(cursor.getInt(cursor.getColumnIndex("habitID")));
            habitInfo[1] = cursor.getString(cursor.getColumnIndex("question"));
        }
        cursor.close();

        if (habitInfo == null) {
            // If habit is not found in CommonHabits table, check CustomHabits table
            cursor = db.rawQuery("SELECT habitID, question FROM CustomHabits WHERE habitName=?", new String[]{habitName});
            if (cursor.moveToFirst()) {
                habitInfo = new String[2];
                habitInfo[0] = String.valueOf(cursor.getInt(cursor.getColumnIndex("habitID")));
                habitInfo[1] = cursor.getString(cursor.getColumnIndex("question"));
            }
            cursor.close();
        }

        db.close();
        return habitInfo;
    }


    @SuppressLint("Range")
    public int getCommonHabitID(String habitName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int commonHabitID = -1;
        Cursor cursor = db.rawQuery("SELECT habitID FROM CommonHabits WHERE habitName=?", new String[]{habitName});
        if (cursor.moveToFirst()) {
            commonHabitID = cursor.getInt(cursor.getColumnIndex("habitID"));
        }
        cursor.close();
        db.close();
        return commonHabitID;
    }
    @SuppressLint("Range")
    public int getChallengeID(String challengeName) {
        SQLiteDatabase db = getReadableDatabase();
        int challengeID = -1;
        Cursor cursor = db.rawQuery("SELECT challengeID FROM CommunityChallenges WHERE challengeName=?", new String[]{challengeName});
        if (cursor.moveToFirst()) {
            challengeID = cursor.getInt(cursor.getColumnIndex("challengeID"));
        }
        cursor.close();
        db.close();
        return challengeID;
    }


    @SuppressLint("Range")
    public int getTotalPointsForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalPoints = 0;

        Cursor cursor = db.rawQuery("SELECT SUM(points) AS totalPoints FROM user_community_challenges WHERE userId = ?", new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            totalPoints = cursor.getInt(cursor.getColumnIndex("totalPoints"));
            cursor.close();
        }

        db.close();
        return totalPoints;
    }
    public ArrayList<String> getAllUsersFromCommunityChallenges() {
        ArrayList<String> users = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Users.userId, Users.username FROM user_community_challenges INNER JOIN Users ON user_community_challenges.userId = Users.userId", null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex("userId"));
                    @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("username"));
                    String userString = "User ID: " + userId + ", Username: " + username;
                    users.add(userString);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        db.close();
        return users;
    }


    public long insertUserChallenge(Context context, int userId, int challengeId, boolean completionStatus, int points) {
        SQLiteDatabase db = getWritableDatabase();

        // Check if the user has already logged for this challenge
        Cursor cursor = db.rawQuery("SELECT * FROM user_community_challenges WHERE userId = ? AND challengeID = ?", new String[]{String.valueOf(userId), String.valueOf(challengeId)});
        if (cursor != null && cursor.getCount() > 0) {
            // If the user has already logged for this challenge, close the cursor and return -1
            cursor.close();
            db.close();
            return -1;
        }

        // If the user has not logged for this challenge, proceed with insertion
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("challengeID", challengeId);
        values.put("completionStatus", completionStatus ? 1 : 0); // Store boolean as 1 for true, 0 for false
        values.put("points", points);
        long result = db.insert("user_community_challenges", null, values);

        // Close cursor and database connection
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return result;
    }




    public ArrayList<String> getCommunityChallenges() {
        ArrayList<String> activitiesList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        // Fetch common habits associated with the user ID
        Cursor cursor = db.rawQuery("SELECT * " +
                "FROM CommunityChallenges", null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String challengeName = cursor.getString(cursor.getColumnIndex("challengeName"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
                @SuppressLint("Range") String place = cursor.getString(cursor.getColumnIndex("place"));
                String activity = "Challenge: " + challengeName + "\nDescription: " + description +
                        "\nDate: " + date + "\nPlace: " + place;
                activitiesList.add(activity);
            }
            cursor.close();
        }

        db.close();
        return activitiesList;
    }
    @SuppressLint("Range")
    public String getNotificationTime(int userId) {
        SQLiteDatabase db = getReadableDatabase();
        String notificationTime=null;

        Cursor cursor = db.rawQuery("SELECT time FROM Notify WHERE userId = ?", new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            notificationTime = cursor.getString(cursor.getColumnIndex("time"));
            cursor.close();
        }

        db.close();
        return notificationTime;
    }



    public long insertUserHabitForCommon(int userId, int commonHabitID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("commonHabitID", commonHabitID);
        values.putNull("customHabitID"); // Set customHabitID as null
        long result = db.insert("UserHabits", null, values);
        db.close();
        return result;
    }
    public void deleteUserHabit(int userId, String habitName) {
        SQLiteDatabase db = getWritableDatabase();
        // Delete the user habit based on the provided user ID and habit name
        db.execSQL("DELETE FROM UserHabits WHERE userId = ? AND commonHabitID IN (SELECT habitID FROM CommonHabits WHERE habitName = ?)", new String[]{String.valueOf(userId), habitName});
        db.close();
    }
    public long insertCustomHabit(String habitName, String question, String frequency, String notes, int reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("habitName", habitName);
        values.put("question", question);
        values.put("frequency", frequency);
        values.put("notes", notes);
        values.put("reminder", reminder);
        long habitID = db.insert("CustomHabits", null, values);
        db.close();
        return habitID;
    }




    public long insertUserHabitForCustom(int userId, long customHabitID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.putNull("commonHabitID"); // Set commonHabitID as null
        values.put("customHabitID", customHabitID);
        long result = db.insert("UserHabits", null, values);
        db.close();
        return result;
    }

    public long insertCommunityChallenge(String challengeName, String description, String date, String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("challengeName", challengeName);
        values.put("description", description);
        values.put("date", date);
        values.put("place", place);

        long result = db.insert("CommunityChallenges", null, values);
        db.close();
        return result;
    }
}