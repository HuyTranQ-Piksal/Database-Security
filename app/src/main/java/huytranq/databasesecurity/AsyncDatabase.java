package huytranq.databasesecurity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by huytr on 04-01-2016.
 */
public class AsyncDatabase extends AsyncTask<Void , Void , Void> {

    private TextView status;
    private int counter;
    private String databasePath;
    private Security cryptoSecretKey;
    private SQLiteDatabase database;
    private Context context;

    public AsyncDatabase(Context context , String databasePath , int mode , TextView status) {
        this.context = context;
        this.databasePath = databasePath;
        this.status = status;
        if (mode == 0)
            cryptoSecretKey = new Encrypter();
        else if (mode == 1)
            cryptoSecretKey = new Decrypter();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        status.setText("Ready...");
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (!existsDatabase()) {
            try {
                InputStream inputStream = context.getAssets().open("databases/eflashcard.db");
                OutputStream outputStream = new FileOutputStream(databasePath);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer , 0 , length);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            }
            catch (Exception exception) {
                Log.d("Exception" , exception.getMessage());
                return null;
            }
        }
        database = SQLiteDatabase.openDatabase(databasePath , null , SQLiteDatabase.OPEN_READWRITE);
        String query = "SELECT * FROM Word";
        List<Word> words = new LinkedList<>();
        counter = 0;
        try {
            Cursor cursor = database.rawQuery(query , null);
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String meaning = cursor.getString(2);
                String example = cursor.getString(4);
                String pronoun = cursor.getString(5);
                String exampleTrans = cursor.getString(8);
                Word word = new Word(id , name , meaning , example , pronoun , exampleTrans);
                words.add(word);
                ++counter;
                publishProgress();
            }
        } catch (Exception exception) {
            Log.d("Exception" , "\n Reading Database: " + exception.getMessage());
        }

        ListIterator<Word> it = words.listIterator();
        while (it.hasNext()) {
            Word word = it.next();
            ContentValues values = new ContentValues();
            values.put(Word.NAME , cryptoSecretKey.digest(word.getName()));
            values.put(Word.MEANING , cryptoSecretKey.digest(word.getMeaning()));
            values.put(Word.EXAMPLE , cryptoSecretKey.digest(word.getExample()));
            values.put(Word.PRONOUN , cryptoSecretKey.digest(word.getPronoun()));
            values.put(Word.EXAMPLE_TRANS , cryptoSecretKey.digest(word.getExampleTrans()));
            if (1 == database.update("Word" , values , Word.ID + " = " + String.valueOf(word.getId()) , null)) {
                --counter;
                publishProgress();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        status.setText(cryptoSecretKey.getName() +  " is Completed");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        status.setText(String.valueOf(counter));
    }

    private boolean existsDatabase() {
        try {
            File file = new File(databasePath);
            return file.exists();
        } catch (SQLiteException exception) {
            Log.d("Exception" , exception.getMessage());
        }
        return false;
    }
}
