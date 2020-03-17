package com.example.tema2;

import android.content.Context;
import android.os.AsyncTask;

import com.example.tema2.models.Mark;

import java.util.List;

import listeners.DeleteListener;
import listeners.GetMarksListener;
import listeners.InsertMarkListener;

public class MarkRepository {
   private AppDatabase appDatabase;
   public MarkRepository(Context context) {
            appDatabase = ApplicationController.getAppDatabase();
   }

   public void insertMarkAsync(final Mark mark,
                               final InsertMarkListener listener) {
            new InsertAsync(listener).execute(mark);
   }

    public void getMarksAsync(final GetMarksListener listener) {
        new GetMarksAsync(listener).execute();
    }

    public void deleteMarkAsync(String name, final DeleteListener listener) {
        new DeleteAsync(listener).execute(name);
    }

    public Mark getMarkByName(String name){
        return appDatabase.markDAO().findByName(name);
    }

    private class InsertAsync extends AsyncTask<Mark, Void, Void> {
        InsertMarkListener listener;
        InsertAsync(InsertMarkListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Mark... users) {
            appDatabase.markDAO().insertAll(users[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.actionSuccess();
        }
    }

    private class GetMarksAsync extends AsyncTask<Void, Void, List<Mark>> {
        GetMarksListener listener;

        GetMarksAsync(GetMarksListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<Mark> doInBackground(Void... voids) {
            return appDatabase.markDAO().getAll();
        }

        @Override
        protected void onPostExecute(List<Mark> marks) {
            super.onPostExecute(marks);
            listener.actionSuccess(marks);
        }
    }

    private class DeleteAsync extends AsyncTask<String, Void, Boolean> {
        DeleteListener listener;

        DeleteAsync(DeleteListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(String... names) {
            for(String name : names){
                Mark mark = getMarkByName(name);

                if(mark == null){
                    return false;
                }

                appDatabase.markDAO().delete(mark);
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean wasSuccessful) {
            super.onPostExecute(wasSuccessful);
            listener.deleteResult(wasSuccessful);
        }
    }
}
