package ar.edu.unc.famaf.redditreader.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by fuentald on 05/11/2016.
 */
public class RedditDBHelper extends SQLiteOpenHelper {
    private static RedditDBHelper sInstance = null;


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RedditDB.db";

    private static final String REDDIT_TABLE = "reddit";

    private static final String _ID = "id";
    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final String CREATED = "created";
    private static final String SUBREDDIT = "subreddit";
    private static final String COMMENTS = "comments";
    private static final String IMAGE = "image";
    private static final String URL = "url";

    private RedditDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized RedditDBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RedditDBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REDDIT_TABLE = "CREATE TABLE " + REDDIT_TABLE + "("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TITLE + " TEXT,"
                + AUTHOR + " TEXT,"
                + CREATED + " TEXT,"
                + SUBREDDIT + " TEXT,"
                + COMMENTS + " TEXT,"
                + IMAGE + " BLOB,"
                + URL + " TEXT"
                + ")";
        db.execSQL(CREATE_REDDIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + REDDIT_TABLE);
        onCreate(db);
    }


    // Adding new post
    public void addPost(PostModel postModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, postModel.getTitle());
        values.put(AUTHOR, postModel.getAuthor());
        values.put(CREATED, postModel.getCreated());
        values.put(SUBREDDIT, postModel.getSubreddit());
        values.put(COMMENTS, postModel.getComments());
        //values.put(IMAGE, postModel.getImageBytes());
        values.put(URL, postModel.getUrl());

        db.insert(REDDIT_TABLE, null, values);
        db.close();
    }

    // Getting single post
    // public PostModel getPost(int id) {}

    // Getting All posts
    public List<PostModel> getAllPosts() {
        List<PostModel> postModelList = new ArrayList<PostModel>();
        String selectQuery = "SELECT  * FROM " + REDDIT_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                PostModel postModel = new PostModel();
                postModel.setTitle(cursor.getString(1));
                postModel.setAuthor(cursor.getString(2));
                postModel.setCreated(cursor.getString(3));
                postModel.setSubreddit(cursor.getString(4));
                postModel.setComments(cursor.getString(5));
                //postModel.setImage(cursor.getBlob(6));
                postModel.setUrl(cursor.getString(7));

                postModelList.add(postModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return postModelList;

    }

    // Getting posts Count
    public int getPostsCount() {
        String countQuery = "SELECT  * FROM " + REDDIT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateImage(String title, Bitmap bitmap){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(IMAGE, getBytes(bitmap));

        return db.update(REDDIT_TABLE, values, TITLE + " = ?",
                new String[] { String.valueOf(title) });
    }

    public Bitmap getImage(String title) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(REDDIT_TABLE, null, TITLE + " LIKE ?",
                new String[] { String.valueOf(title) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();



        return getImage(cursor.getBlob(0));
    }


    // Updating single post
    // public int updatePost(PostModel postModel) {}

    // Deleting All posts
    public void deleteAllPosts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(REDDIT_TABLE, null, null);
        db.close();
    }


    private byte[] getBytes(Bitmap bitmap)
    {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,0, stream);
        return stream.toByteArray();
    }

    private Bitmap getImage(byte[] image)
    {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}

