package my.edu.tarc.lab42database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;

//TODO 6: Create a repository class to manage data query thread

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        //Get or create an instance of database
        AppDatabase db = AppDatabase.getDatabase(application);
        //Get an instance of DAO
        userDao = db.userDao();
        //Retrieve all the user records
        allUsers = userDao.loadAllUsers();
    }

    //Feed the LiveData with all user records
    LiveData<List<User>> getAllUsers(){
        return allUsers;
    }
    
    public void insertUser(User user){

        new insertAsyncTask(userDao).execute(user);
    }

    public void deleteUser(User user){
        new deleteAsyncTask(userDao).execute(user);
    }

    public void updateUser(User user) {
        new updateAsyncTask(userDao).execute(user);
    }

    //<Param, Progress, Results>
    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public insertAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUser(users[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public deleteAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.deleteUser(users[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public updateAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.updateUser(users[0]);
            return null;
        }
    }
}
