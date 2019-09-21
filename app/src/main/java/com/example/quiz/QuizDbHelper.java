package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.quiz.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable() {
        Category c1 = new Category("History");
        insertCategory(c1);
        Category c2 = new Category("Geography");
        insertCategory(c2);
        Category c3 = new Category("Econimics");
        insertCategory(c3);
    }

    public void addCategory(Category category) {
        db = getWritableDatabase();
        insertCategory(category);
    }

    public void addCategories(List<Category> categories) {
        db = getWritableDatabase();

        for (Category category : categories) {
            insertCategory(category);
        }
    }

    private void insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Who among the following foreigners was the first to visit India?",
                "A. Hiuen-Tsang", "B. Fahien", "C. Megasthenese", 3,
                Question.DIFFICULTY_EASY, Category.HISTORY);
        insertQuestion(q1);
        Question q2 = new Question("Bimbisara belonged to which of the following dynasties?",
                "A. Haryanka", "B. Nanda", "C. Mauryan", 1,
                 Question.DIFFICULTY_EASY, Category.HISTORY);
        insertQuestion(q2);
        Question q3 = new Question("The Ajanta painting belongs to the",
                "A. Harappan period", "B. Buddhist period", "C. Gupta period",3,
                Question.DIFFICULTY_EASY, Category.HISTORY);
        insertQuestion(q3);
        Question q4 = new Question("Who of the following was a contemporary of Alexander the Great?",
                "A. Chandragupta Maurya", "B. Ashoka", "C. Bimbisara", 1,
                Question.DIFFICULTY_EASY, Category.HISTORY);
        insertQuestion(q4);
        Question q5 = new Question("Vindhyashakti was founder of which of the following dynasties in ancient India?",
                "A. Kalachuri", "B. Kakatiya", "C. Vakataka", 3,
                Question.DIFFICULTY_EASY, Category.HISTORY);
        insertQuestion(q5);
        Question q6 = new Question("What was “Halivakara” in the Gupta Era? ",
                "A. A kind of Play", "B. A kind of Tax", "C. A kind of Tribe", 2,
                Question.DIFFICULTY_MEDIUM, Category.HISTORY);
        insertQuestion(q6);
        Question q7 = new Question("Who among the following succeeded Pushyamitra Shunga?",
                "A. Agnimitra", "B. Sujyestha", "C. Vajramitra", 1,
                Question.DIFFICULTY_MEDIUM, Category.HISTORY);
        insertQuestion(q7);
        Question q8 = new Question("In which year, Nalanda University was finally destroyed?",
                "A. 1193 AD", "B. 1093 AD", "C. 1293 AD", 1,
                Question.DIFFICULTY_MEDIUM, Category.HISTORY);
        insertQuestion(q8);
        Question q9 = new Question("Which among the following was the capital of Vatsa Mahajanapada?",
                "A. Mathura", "B. Kausambi", "C. Kashi", 2,
                Question.DIFFICULTY_MEDIUM, Category.HISTORY);
        insertQuestion(q9);
        Question q10 = new Question("who among the following had written “Rajavalipataka”?",
                "A. Pandit Jonaraja", "B. Pandit Shrivara", "C. Pandit Prajabhatta", 3,
                Question.DIFFICULTY_MEDIUM, Category.HISTORY);
        insertQuestion(q10);
        Question q11 = new Question("Which pillar edict of Asoka is longest of all pillars?",
                "A. 5th", "B. 6th", "C. 7th", 3,
                Question.DIFFICULTY_HARD, Category.HISTORY);
        insertQuestion(q11);
        Question q12 = new Question("Who among the following belonged to Sakya clan of Kshatriyas?",
                "A. Gautam Buddha", "B. Mahavir jain", "C. Rishabhnath", 1,
                Question.DIFFICULTY_HARD, Category.HISTORY);
        insertQuestion(q12);
        Question q13 = new Question("Which among the following is a ritualistic Veda?",
                "A. Yajurveda", "B. Samaveda", "C. Atharvaveda", 1,
                Question.DIFFICULTY_HARD, Category.HISTORY);
        insertQuestion(q13);
        Question q14 = new Question("Painted Grey ware” was used in which of the following era?",
                "A. Early Vedic Era", "B. Harappan Era", "C. Later Vedic Era", 3,
                Question.DIFFICULTY_HARD, Category.HISTORY);
        insertQuestion(q14);
        Question q15 = new Question("Partha was the name of which of the following character in Mahabharta?",
                "A. Arjuna", "B. Bhima", "C. Yudhisthara", 1,
                Question.DIFFICULTY_HARD, Category.HISTORY);
        insertQuestion(q15);


        Question q16 = new Question("The “Ninety East Ridge” is a submarine volcanic ridge located in __?",
                "A. Atlantic Ocean","B. Indian Ocean","C. Arctic Ocean",2,
                Question.DIFFICULTY_EASY,Category.GEOGRAPHY);
        insertQuestion(q16);
        Question q17 = new Question("Which of the following countries are separated by the Strait of Gibraltar?",
                "A. Morroco and Spain","B. Algeria and Portugal","C. Algeria and Spain",1,
                Question.DIFFICULTY_EASY,Category.GEOGRAPHY);
        insertQuestion(q17);
        Question q18 = new Question("Laterite soils’ are formed due which of the following actions?",
                "A. Deposition of fertile riverine silt","B. Erosion","C. Leaching",3,
                Question.DIFFICULTY_EASY,Category.GEOGRAPHY);
        insertQuestion(q18);
        Question q19 = new Question("‘Oxus’ is the ancient name of which of the following rivers?",
                "A. Euphrates","B. Syr Darya","C. Amu Darya",3,
                Question.DIFFICULTY_EASY,Category.GEOGRAPHY);
        insertQuestion(q19);
        Question q20 = new Question("Falkland Island is a disputed territory between :",
                "A. Uruguay and France","B. Argentina and UK","C. Chile and Equador",2,
                Question.DIFFICULTY_EASY,Category.GEOGRAPHY);
        insertQuestion(q20);
        Question q21 = new Question("Cape Agulhas makes the dividing point between which of the two oceans?",
                "A. Indian Ocean and Pacific Ocean","B. Atlantic Ocean and Indian Ocean","C. Pacific Ocean and Atlantic Ocean",2,
                Question.DIFFICULTY_MEDIUM,Category.GEOGRAPHY);
        insertQuestion(q21);
        Question q22 = new Question("The Fluvial Landforms are created by ___",
                "A. Chemicals","B. Air","C. Water",3,
                Question.DIFFICULTY_MEDIUM,Category.GEOGRAPHY);
        insertQuestion(q22);
        Question q23 = new Question("Pohang Iron and Steel Company is from which among the following countries?",
                "A. Japan","B. South Korea","C. China",2,
                Question.DIFFICULTY_MEDIUM,Category.GEOGRAPHY);
        insertQuestion(q23);
        Question q24 = new Question("The “Registan Desert” is a part of which among the following countries?",
                "A. Turkmenistan","B. Tajikistan","C. Afghanistan",3,
                Question.DIFFICULTY_MEDIUM,Category.GEOGRAPHY);
        insertQuestion(q24);
        Question q25 = new Question("Mount Kilimanjaro is located in which country ?",
                "A. Kenya","B. Tanzania","C. Tanzania",2,
                Question.DIFFICULTY_MEDIUM,Category.GEOGRAPHY);
        insertQuestion(q25);
        Question q26 = new Question("Asthenosphere is located at?",
                "A. Below Lithosphere","B. Above Lithosphere","C. Below Ionsphere",1,
                Question.DIFFICULTY_HARD,Category.GEOGRAPHY);
        insertQuestion(q26);
        Question q27 = new Question("Mount Logan is the highest point of which among the following countries?",
                "A. Brazil","B. Mexico","C. Canada",3,
                Question.DIFFICULTY_HARD,Category.GEOGRAPHY);
        insertQuestion(q27);
        Question q28 = new Question("Margosa oil is obtained from which of the following trees?",
                "A. Babul","B. Neem","C. Ficus",2,
                Question.DIFFICULTY_HARD,Category.GEOGRAPHY);
        insertQuestion(q28);
        Question q29 = new Question("Shakespeare beach is located in which country?",
                "A. England","B. Italy","C. France",1,
                Question.DIFFICULTY_HARD,Category.GEOGRAPHY);
        insertQuestion(q29);
        Question q30 = new Question("Chuquicamata in Chile is famous for which of the following ores?",
                "A. Zinc","B. Copper","C. Iron",2,
                Question.DIFFICULTY_HARD,Category.GEOGRAPHY);
        insertQuestion(q30);

        Question q31 = new Question("The cost of one thing in terms of the alternative given up is called:",
                "A. opportunity cost", "B. Physical cost", "C. Real cost", 1,
                Question.DIFFICULTY_EASY, Category.ECONOMICS);
        insertQuestion(q31);
        Question q32 = new Question("Which statistical measure helps in measuring the purchasing power of money?",
                "A. Time series", "B. Harmonic mean", "C. Index numbers", 3,
                Question.DIFFICULTY_EASY, Category.ECONOMICS);
        insertQuestion(q32);
        Question q33 = new Question("Who is the ‘lender of the last resort’ in the banking structure of India?",
                "A. Reserve Bank of India", "B. State Bank of India", "C. Union Bank of India", 1,
                Question.DIFFICULTY_EASY, Category.ECONOMICS);
        insertQuestion(q33);
        Question q34 = new Question("Which among the following is a cause of inflation?",
                "A. Rise in external loans", "B. Deficit financing", "C. Unfavourable balance of payment", 2,
                Question.DIFFICULTY_EASY, Category.ECONOMICS);
        insertQuestion(q34);
        Question q35 = new Question("Cost push inflation occurs because of:",
                "A. Profit push", "B. Wage push", "C. Both A and B", 3,
                Question.DIFFICULTY_EASY, Category.ECONOMICS);
        insertQuestion(q35);
        Question q36 = new Question("Monetary policy is implemented by in India.",
                "A. Planning Commission","B. Reserve Bank of India","C. The Ministry of Finance",2,
                Question.DIFFICULTY_MEDIUM, Category.ECONOMICS);
        insertQuestion(q36);
        Question q37 = new Question("Which country was the first to adopt a gold standard in the modern sense?",
                "A. Great Britain","B. France","C. Portugal",1,
                Question.DIFFICULTY_MEDIUM, Category.ECONOMICS);
        insertQuestion(q37);
        Question q38 = new Question("What have been the reasons of deficit in India’s Balance of Trade in the past?",
                "A. Modest growth of exports","B. Very large rise in imports","C. All of the above",3,
                Question.DIFFICULTY_MEDIUM, Category.ECONOMICS);
        insertQuestion(q38);
        Question q39 = new Question("Which five year plan in India gave emphasis on Co-operative Federalism?",
                "A. Ninth five year plan","B. Tenth five year plan","C. Twelfth five year plan",1,
                Question.DIFFICULTY_MEDIUM, Category.ECONOMICS);
        insertQuestion(q39);
        Question q40 = new Question("Who has contributed the modem theory of interest rate determination?",
                "A. Gunnar Myrdal","B. Knut Wicksell","C. J.R. Hicks",3,
                Question.DIFFICULTY_MEDIUM, Category.ECONOMICS);
        insertQuestion(q40);
        Question q41 = new Question("Which of the following is known as long run average cost curve?",
                "A. Learning curve","B. Envelope curve","C. Phillips curve",2,
                Question.DIFFICULTY_HARD, Category.ECONOMICS);
        insertQuestion(q41);
        Question q42 = new Question("Which market structure symbolizes the existence of ‘few sellers’?",
                "A. Oligopoly","B. Monopoly","C. Perfect competition",1,
                Question.DIFFICULTY_HARD, Category.ECONOMICS);
        insertQuestion(q42);
        Question q43 = new Question("On which law of consumption the concept of consumer’s surplus is based?",
                "A. Law of demand","B. First law of Gossen","C. Engel’s law",2,
                Question.DIFFICULTY_HARD, Category.ECONOMICS);
        insertQuestion(q43);
        Question q44 = new Question("Under conditions of perfect competition in the product market:",
                "A. VMP > MRP","B. MRP > VMP","C. MRP=VMP",3,
                Question.DIFFICULTY_HARD, Category.ECONOMICS);
        insertQuestion(q44);
        Question q45 = new Question("Credit creation power of the commercial banks gets limited by which of the following?",
                "A. Banking habits of the people","B. Cash reserve ratio","C. All of the above",3,
                Question.DIFFICULTY_HARD, Category.ECONOMICS);
        insertQuestion(q45);
    }

    public void addQuestion(Question question) {
        db = getWritableDatabase();
        insertQuestion(question);
    }

    public void addQuestions(List<Question> questions) {
        db = getWritableDatabase();

        for (Question question : questions) {
            insertQuestion(question);
        }
    }

    private void insertQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }

        c.close();
        return categoryList;
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};

        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}