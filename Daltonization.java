package my.project.MyRealTimeImageProcessing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.neuroph.core.NeuralNetwork;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.w3c.dom.Text;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.neuroph.core.Weight;
import pl.droidsonroids.gif.GifTextView;


public class Daltonization extends AppCompatActivity
{
    private AlphaAnimation btnclickEffect = new AlphaAnimation(1F, 0.8F);
    GifTextView gifTitle;
    boolean doubleBackToExitPressedOnce = false;

    // Daltonize Variables
    private CameraPreview camPreview;
    private ImageView MyCameraPreview;
    private RelativeLayout mainLayout;
    private int PreviewSizeWidth = 480;
    private int PreviewSizeHeight= 640;
    private boolean dalt = true;
    private int intDaltType = -1;

    // Ishihara Test Variables
    private Button Start,Records, About, btn1, btn2, btn3, btnNext, B1, B2, B3;
    private ImageButton imgbtn1, imgbtn2, imgbtn3, iB1, iB2, iB3;
    private CountDownTimer questionTimer;
    private int intCtr = -1;
    private int intChoiceNum;
    private double dblInput[] = { 0, 0, 0, 0, 0, 0};
    private int intInput[];
    private int intSelectedChoice;
    private int intAnswers[] = {
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0
    };
    int intValue[][] = {
            /*1*/ { 0, 1, 0}, /*2*/ { 0, 1, 0},
			/*3*/ { 1, 0, 0}, /*4*/ { 0, 1, 0},
			/*5*/ { 0, 1, 0}, /*6*/ { 0, 1, 0},
			/*7*/ { 1, 0, 0}, /*8*/ { 1, 0, 0},
			/*9*/ { 0, 1, 0}, /*10*/{ 0, 1, 0},
			/*11*/{ 0, 1, 0}, /*12*/{ 0, 1, 0},
			/*13*/{ 0, 1, 0}, /*14*/{ 0, 1, 0},
			/*15*/{ 0, 1, 0}, /*16*/{ 0, 1, 0},
			/*17*/{ 0, 1, 0}, /*18*/{ 1, 0, 0},
			/*19*/{ 1, 0, 0}, /*20*/{ 1, 0, 0},
			/*21*/{ 1, 0, 0}, /*22*/{ 0, 1, 2},
			/*23*/{ 0, 1, 2}, /*24x0*/{ 0, 3, 1},
			/*25x0*/{ 0, 3, 1},	/*26x0*/{ 0, 3, 1},
			/*27x0*/{ 0, 3, 1},	/*28*/{ 1, 0, 0},
			/*29*/{ 1, 0, 0}, /*30*/{ 0, 1, 0},
			/*31*/{ 0, 1, 0}, /*32*/{ 0, 1, 0},
			/*33*/{ 0, 1, 0}, /*34*/{ 0, 1, 0},
			/*35*/{ 0, 1, 0}, /*36*/{ 0, 1, 0},
			/*37*/{ 0, 1, 0}, /*38*/{ 0, 1, 0},
			/*24x1*/{ 2, 1, 0}, /*24x2*/{ 4, 3, 0},
			/*25x1*/{ 2, 1, 0}, /*25x2*/{ 4, 3, 0},
			/*26x1*/{ 2, 1, 0}, /*26x2*/{ 4, 3, 0},
			/*27x1*/{ 2, 1, 0}, /*27x2*/{ 4, 3, 0},
    };
    String strChoices[][] = {
            /*1*/ {"12", "Nothing", "0"},
			/*2*/ {"8", "3", "0"},
			/*3*/ {"5", "6", "0"},
			/*4*/ {"29", "70", "0"},
			/*5*/ {"57", "35", "0"},
			/*6*/ {"5", "2", "0"},
			/*7*/ {"5", "3", "0"},
			/*8*/ {"17", "15", "0"},
			/*9*/ {"74", "21", "0"},
			/*10*/{"2", "Nothing", "0"},
			/*11*/{"6", "Nothing", "0"},
			/*12*/{"97", "Nothing", "0"},
			/*13*/{"45", "Nothing", "0"},
			/*14*/{"5", "Nothing", "0"},
			/*15*/{"7", "Nothing", "0"},
			/*16*/{"16", "Nothing", "0"},
			/*17*/{"73", "Nothing", "0"},
			/*18*/{"5", "Nothing", "0"},
			/*19*/{"2", "Nothing", "0"},
			/*20*/{"45", "Nothing", "0"},
			/*21*/{"73", "Nothing", "0"},
			/*22*/{"Two Lines", "pic26d", "pic26p"},
			/*23*/{"Two Lines", "pic27d", "pic27p"},
			/*24x0*/{"26", "(2)6", "2(6)"},
			/*25x0*/{"42", "(4)2", "4(2)"},
			/*26x0*/{"35", "(3)5", "3(5)"},
			/*27x0*/{"96", "(9)6", "9(6)"},
			/*28*/{"A line", "Nothing", "0"},
			/*29*/{"A line", "Nothing", "0"},
			/*30*/{"A line", "Nothing", "0"},
			/*31*/{"A line", "Nothing", "0"},
			/*32*/{"pic32normal", "Nothing", "0"},
			/*33*/{"pic33normal", "Nothing", "0"},
			/*34*/{"pic34normal", "pic34cvd", "0"},
			/*35*/{"pic35normal", "pic35cvd", "0"},
			/*36*/{"pic36normal", "pic36cvd", "0"},
			/*37*/{"pic37normal", "pic37cvd", "0"},
			/*38*/{"A line", "Nothing", "0"},
			/*24x1*/{"2", "2(6)", "0"},
			/*24x2*/{"6", "(2)6", "0"},
			/*25x1*/{"4", "4(2)", "0"},
			/*25x2*/{"2", "(4)2", "0"},
			/*26x1*/{"3", "3(5)", "0"},
			/*26x2*/{"5", "(3)5", "0"},
			/*27x1*/{"9", "9(6)", "0"},
			/*27x2*/{"6", "(9)6", "0"}
    };

    @Override
    @SuppressLint("ResourceType")
    public void onCreate(Bundle savedInstanceState){
        // Camera Permission for API 23 and up
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 50);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setupToolbar();
        welcome();
    }

    private void setupToolbar(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(220, 180, 0)));
        getSupportActionBar().hide();
    }

    @SuppressLint("ResourceType")
    private void welcome(){
//        final RelativeLayout rel = (RelativeLayout) findViewById(R.id.relativeLayout1);
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout rel = (RelativeLayout) findViewById(R.id.relativeLayout1);
        rel.setBackgroundColor(Color.rgb(220,180,0));

        LinearLayout lin = (LinearLayout) findViewById(R.id.linearLayout1);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        gifTitle = (GifTextView) findViewById(R.id.giftitle);
        gifTitle.setVisibility(View.VISIBLE);

        Button btnStart = new Button(this);
        Button btnRecords = new Button(this);
        Button btnAbout = new Button(this);

        btnStart.setId(0);
        btnRecords.setId(7);
        btnAbout.setId(8);

        // Turn off auto UPPERCASE in button's text (default in API 21+)
        btnStart.setTransformationMethod(null);
        btnRecords.setTransformationMethod(null);
        btnAbout.setTransformationMethod(null);

        // Button Font
        Typeface btnFont = Typeface.createFromAsset(getAssets(),"fonts/RobotoL.ttf");

        // Button Background
        GradientDrawable btnBG = new GradientDrawable();
        btnBG.setColor(Color.rgb(0,0,0));
        btnBG.setCornerRadius(25);

        btnStart.setText("Start");
        lp.bottomMargin = 45;
        btnStart.setLayoutParams(lp);
        btnStart.setTypeface(btnFont);
        btnStart.setTextSize(24);
        btnStart.setTextColor(Color.rgb(255,255,255));
        btnStart.setBackground(btnBG);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeMainViews();
                start();
//                createQuestion();
//                Daltonize() ;
            }
        });

        btnRecords.setText("Records");
        lp.topMargin = 0;
        btnRecords.setLayoutParams(lp);
        btnRecords.setTypeface(btnFont);
        btnRecords.setTextSize(24);
        btnRecords.setTextColor(Color.rgb(255,255,255));
        btnRecords.setBackground(btnBG);
        btnRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeMainViews();
                records();
            }
        });

        btnAbout.setText("About");
        btnAbout.setTypeface(btnFont);
        btnAbout.setTextSize(24);
        btnAbout.setTextColor(Color.rgb(255,255,255));
        btnAbout.setBackground(btnBG);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeMainViews();
                About.startAnimation(btnclickEffect);
            }
        });

        lin.addView(btnStart);
        lin.addView(btnRecords);
        lin.addView(btnAbout);
    }

    private void start(){
        getSupportActionBar().show();
        getSupportActionBar().setTitle("Cool-Eye");
        LinearLayout lin = (LinearLayout) findViewById(R.id.linStart);
        lin.setVisibility(View.VISIBLE);

        Typeface tvFont = Typeface.createFromAsset(getAssets(),"fonts/RobotoL.ttf");

        TextView tvFill = (TextView) findViewById(R.id.tvFillOut);
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvAge = (TextView) findViewById(R.id.tvAge);
        TextView tvOr = (TextView) findViewById(R.id.tvOr);
        TextView tvGoDalt = (TextView) findViewById(R.id.tvGoDalt);

        tvFill.setTypeface(tvFont);
        tvName.setTypeface(tvFont);
        tvAge.setTypeface(tvFont);
        tvOr.setTypeface(tvFont);
        tvGoDalt.setTypeface(tvFont);

        tvFill.setTextSize(21);
        tvName.setTextSize(22);
        tvAge.setTextSize(22);
        tvOr.setTextSize(25);
        tvGoDalt.setTextSize(21);

        EditText etName = (EditText) findViewById(R.id.etName);
        EditText etAge = (EditText) findViewById(R.id.etAge);

        etName.setTypeface(tvFont);
        etAge.setTypeface(tvFont);

        etName.setTextSize(23);
        etAge.setTextSize(23);

        final GradientDrawable BGbtn = new GradientDrawable();
        BGbtn.setColor(Color.rgb(220,180,0));
        BGbtn.setCornerRadius(20);
        final GradientDrawable BGbtnClicked = new GradientDrawable();
        BGbtnClicked.setColor(Color.rgb(150,110,0));
        BGbtnClicked.setCornerRadius(25);
        GradientDrawable BGbtnBlack = new GradientDrawable();
        BGbtnBlack.setColor(Color.rgb(30,30,30));
        BGbtnBlack.setCornerRadius(25);

        Button btnBegin = (Button) findViewById(R.id.btnBegin);
        final Button btnDeut = (Button) findViewById(R.id.btnDeut);
        final Button btnProt = (Button) findViewById(R.id.btnProt);
        Button btnGoDalt = (Button) findViewById(R.id.btnGoDalt);

        btnBegin.setBackground(BGbtnBlack);
        btnDeut.setBackground(BGbtn);
        btnProt.setBackground(BGbtn);
        btnGoDalt.setBackground(BGbtnBlack);

        btnBegin.setTypeface(tvFont);
        btnDeut.setTypeface(tvFont);
        btnProt.setTypeface(tvFont);
        btnGoDalt.setTypeface(tvFont);

        btnBegin.setTextSize(23);
        btnDeut.setTextSize(21);
        btnProt.setTextSize(21);
        btnGoDalt.setTextSize(23);

        btnBegin.setTextColor(Color.rgb(255,255,255));
        btnGoDalt.setTextColor(Color.rgb(255,255,255));

        tvFill.setText("Please fill out the following before proceeding:");
        tvName.setText("Name:");
        tvAge.setText("Age:");
        tvOr.setText("or");
        tvGoDalt.setText("Go straight to correction (daltonization) camera:");

        btnBegin.setText("Begin");
        btnDeut.setText("Deuteranopia");
        btnProt.setText(" Protanopia ");
        btnGoDalt.setText("Daltonize!");

        intDaltType = -1;

        btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeStartViews();
                createQuestion();
            }
        });

        btnDeut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intDaltType = 0;    // Deuteranopia
                btnDeut.setBackground(BGbtnClicked);
                btnProt.setBackground(BGbtn);
            }
        });

        btnProt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intDaltType = 1;    // Protanopia
                btnProt.setBackground(BGbtnClicked);
                btnDeut.setBackground(BGbtn);
            }
        });

        btnGoDalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((intDaltType == 0) || (intDaltType == 1)){
                    getSupportActionBar().hide();
                    removeStartViews();
                    Daltonize();
                }
            }
        });

    }

    @SuppressLint("ResourceType")
    private void records(){
        LinearLayout lin = (LinearLayout) findViewById(R.id.linearLayout3);
        LinearLayout list = (LinearLayout) findViewById(R.id.parentLayout);
        lin.setVisibility(View.VISIBLE);
        getSupportActionBar().show();
        getSupportActionBar().setTitle("Records");

        String strList = getList();
        final String[] strListByLine = strList.split("\n");
        // If file is empty
        if (strListByLine.length < 1){
            TextView tvNoRecords = (TextView) findViewById(R.id.tvNoRecordsFound);
            tvNoRecords.setVisibility(View.VISIBLE);
        } else {

            for(int i = 0; i < strListByLine.length ; i++){
                final TextView rowTextView = new TextView(this);
                rowTextView.setText(strListByLine[i]);
                list.addView(rowTextView);
            }
        }
    }

    @SuppressLint("ResourceType")
    private void createQuestion() {
//        RelativeLayout t1 = (RelativeLayout) findViewById(R.id.relativeLayout1);
        LinearLayout lin = (LinearLayout) findViewById(R.id.linearLayout2);

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/RobotoL.ttf");

        Button btn1 = new Button(this);
        Button btn2 = new Button(this);
        Button btn3 = new Button(this);
        ImageButton imgbtn1 = new ImageButton(this);
        ImageButton imgbtn2 = new ImageButton(this);
        ImageButton imgbtn3 = new ImageButton(this);
        Button btnNext = new Button(this);

        btn1.setId(1);
        btn2.setId(2);
        btn3.setId(3);
        imgbtn1.setId(11);
        imgbtn2.setId(12);
        imgbtn3.setId(13);
        btnNext.setId(4);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.weight = 1;
        btn1.setLayoutParams(lp);
        btn2.setLayoutParams(lp);
        btn3.setLayoutParams(lp);
        imgbtn1.setLayoutParams(lp);
        imgbtn2.setLayoutParams(lp);
        imgbtn3.setLayoutParams(lp);

        btnNext.setEnabled(false);

        btn1.setText(strChoices[0][1]);
        btn1.setTextSize(35);
        btn1.setTypeface(tf);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) findViewById(4);
                btn.setEnabled(true);
                SelectChoice( 0, 0);
            }
        });

        btn2.setText(strChoices[0][1]);
        btn2.setTextSize(35);
        btn2.setTypeface(tf);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) findViewById(4);
                btn.setEnabled(true);
                SelectChoice( 1, 0);
            }
        });

        btn3.setText(strChoices[0][1]);
        btn3.setTextSize(35);
        btn3.setTypeface(tf);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectChoice( 2, 0);
            }
        });

        imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectChoice( 0, 1);
            }
        });

        imgbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectChoice( 1, 1);
            }
        });

        imgbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectChoice( 2, 1);
            }
        });

        btnNext.setText("NEXT");
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                B1 = (Button) findViewById(1);
                B2 = (Button) findViewById(2);
                B3 = (Button) findViewById(3);
                iB1 = (ImageButton) findViewById(11);
                iB2 = (ImageButton) findViewById(12);
                iB3 = (ImageButton) findViewById(13);
                int color1 = ((ColorDrawable)B1.getBackground()).getColor();
                int color2 = ((ColorDrawable)B2.getBackground()).getColor();
                int color3 = ((ColorDrawable)B3.getBackground()).getColor();
                int color4 = ((ColorDrawable)iB1.getBackground()).getColor();
                int color5 = ((ColorDrawable)iB2.getBackground()).getColor();
                int color6 = ((ColorDrawable)iB3.getBackground()).getColor();
                // One of the choices must be selected to proceed
                if(color1 == Color.GRAY || color2 == Color.GRAY || color3 == Color.GRAY ||
                        color4 == Color.GRAY || color5 == Color.GRAY || color6 == Color.GRAY){
                    getAnswer();
                }
            }
        });

        lin.addView(btn1);
        lin.addView(btn2);
        lin.addView(btn3);
        lin.addView(imgbtn1);
        lin.addView(imgbtn2);
        lin.addView(imgbtn3);
        lin.addView(btnNext);

        questionTimer=  new CountDownTimer(10, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                changeQuestion();
            }
        };
        nextPic();
        getSupportActionBar().show();
        getSupportActionBar().setTitle("Plate " + Integer.toString(intCtr + 2));

    }

    private void SelectChoice(int intChoice, int intType){
        btn1.setBackgroundColor(Color.WHITE);
        btn2.setBackgroundColor(Color.WHITE);
        btn3.setBackgroundColor(Color.WHITE);
        imgbtn1.setBackgroundColor(Color.WHITE);
        imgbtn2.setBackgroundColor(Color.WHITE);
        imgbtn3.setBackgroundColor(Color.WHITE);
        // Highlight Selected Choice
        if( intChoice == 0 && intType == 0) {
            btn1.setBackgroundColor(Color.GRAY);
        }else if( intChoice == 1 && intType == 0){
            btn2.setBackgroundColor(Color.GRAY);
        }else if( intChoice == 2 && intType == 0){
            btn3.setBackgroundColor(Color.GRAY);
        }else if( intChoice == 0 && intType == 1){
            imgbtn1.setBackgroundColor(Color.GRAY);
        }else if( intChoice == 1 && intType == 1){
            imgbtn2.setBackgroundColor(Color.GRAY);
        }else if( intChoice == 2 && intType == 1){
            imgbtn3.setBackgroundColor(Color.GRAY);
        }

        intSelectedChoice = intValue[intCtr][intChoice];
    }

    private void getAnswer(){
        intAnswers[intCtr] = intSelectedChoice;

        if( intCtr == 37){
            getSupportActionBar().hide();
            Decision();
        }else {
            nextPic();
        }
    }

    @SuppressLint("ResourceType")
    private void changeQuestion(){
        getSupportActionBar().setTitle("Plate " + Integer.toString(intCtr + 2));
        ImageView img = (ImageView) findViewById(R.id.image);
        img.setVisibility(View.GONE);
        intChoiceNum = 0;
        intCtr++;
        questionTimer.cancel();
        if (intAnswers[22] != 0 && (intCtr > 22 && intCtr < 27)) {
            intChoiceNum = 37 + (2 * (intCtr - 23)) + intAnswers[22];
        } else {
            intChoiceNum = intCtr;
        }
        if (intCtr < 39) {
            btn1 = (Button) findViewById(1);
            btn2 = (Button) findViewById(2);
            btn3 = (Button) findViewById(3);
            imgbtn1 = (ImageButton) findViewById(11);
            imgbtn2 = (ImageButton) findViewById(12);
            imgbtn3 = (ImageButton) findViewById(13);

            btn1.setBackgroundColor(Color.WHITE);
            btn2.setBackgroundColor(Color.WHITE);
            btn3.setBackgroundColor(Color.WHITE);
            imgbtn1.setBackgroundColor(Color.WHITE);
            imgbtn2.setBackgroundColor(Color.WHITE);
            imgbtn2.setBackgroundColor(Color.WHITE);

            if (strChoices[intChoiceNum][0].startsWith("pic")) {
                String imageName = strChoices[intChoiceNum][0];
                int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());
                imgbtn1.setImageResource(resID);
                imgbtn1.setVisibility(View.VISIBLE);
            } else {
                btn1.setText(strChoices[intChoiceNum][0]);
                btn1.setVisibility(View.VISIBLE);
            }

            if (strChoices[intChoiceNum][1].startsWith("pic")) {
                String imageName = strChoices[intChoiceNum][1];
                int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());
                imgbtn2.setImageResource(resID);
                imgbtn2.setVisibility(View.VISIBLE);
            } else {
                btn2.setText(strChoices[intChoiceNum][1]);
                btn2.setVisibility(View.VISIBLE);
            }

            if (!(strChoices[intChoiceNum][2]).equals("0")) {
                if (strChoices[intChoiceNum][2].startsWith("pic")) {
                    String imageName = strChoices[intChoiceNum][2];
                    int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());
                    imgbtn3.setImageResource(resID);
                    imgbtn3.setVisibility(View.VISIBLE);
                } else {
                    btn3.setText(strChoices[intChoiceNum][2]);
                    btn3.setVisibility(View.VISIBLE);
                }
            }
            btnNext.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("ResourceType")
    private void nextPic() {
        btn1 = (Button) findViewById(1);
        btn2 = (Button) findViewById(2);
        btn3 = (Button) findViewById(3);
        imgbtn1 = (ImageButton) findViewById(11);
        imgbtn2 = (ImageButton) findViewById(12);
        imgbtn3 = (ImageButton) findViewById(13);
        btnNext = (Button) findViewById(4);
        btn1.setVisibility(View.GONE);
        btn2.setVisibility(View.GONE);
        btn3.setVisibility(View.GONE);
        imgbtn1.setVisibility(View.GONE);
        imgbtn2.setVisibility(View.GONE);
        imgbtn3.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
        // Display Plate
        String ImageName = "@drawable/p" + (intCtr + 2);
        int imageResource = getResources().getIdentifier(ImageName, null, getPackageName());
        ImageView img = (ImageView) findViewById(R.id.image);
        img.setImageResource(imageResource);
        img.setVisibility(View.VISIBLE);
        //Toast.makeText(getApplicationContext(), "You pressed " + intChoiceNum, Toast.LENGTH_SHORT).show();
        questionTimer.start();
    }

    @SuppressLint("ResourceType")
    private void Decision(){
        final RelativeLayout t1 = (RelativeLayout) findViewById(R.id.relativeLayout1);
        removeQuestionnaireViews();

        /*for(int intCtr2 = 0; intCtr2 < 27; intCtr2++) {
            dblInput[intCtr2]= intAnswers[intCtr2 + 1];
        }*/
        int intDecision =0;

        for(int intCtr2 = 21; intCtr2 < 27; intCtr2++) {
            dblInput[intCtr2 - 21] = (double) intAnswers[intCtr2];
        }
        int intNormalCounter = 0;
        int intUnclassifiedCtr[] = { 0, 0};
        double dblUnclassifiedValue[] = { dblInput[2], -1};

        for(int intInputIndex1 = 2; intInputIndex1 < 6; intInputIndex1++) {
            if( dblInput[intInputIndex1] == 0){
                intNormalCounter++;
                if( intNormalCounter == 3){
                    intDecision = 100;
                    intInputIndex1 = 5;
                }
            }
            if( dblInput[intInputIndex1] == dblUnclassifiedValue[0]){
                intUnclassifiedCtr[0]++;
            }
            else {
                if( dblUnclassifiedValue[1] == -1){
                    dblUnclassifiedValue[1] = dblInput[intInputIndex1];
                }
                if( dblInput[intInputIndex1] == dblUnclassifiedValue[1]) {
                    intUnclassifiedCtr[1]++;
                }
                if( intUnclassifiedCtr[0] == 2 && intUnclassifiedCtr[1] == 2){
                    intDecision = 99;
                }
            }
        }
        //if input is for ANN
        if ( intDecision != 99 && intDecision != 100){
            intDecision = ANN(dblInput);
        }

        final TextView Answer = new TextView(this);
        Answer.setText(Integer.toString(intDecision) );
        t1.addView(Answer);

        /*final Button btnDalt = new Button(this);
        btnDalt.setId(5);
        btnDalt.setText("Dalt");
        btnDalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.removeView(btnDalt);
//                t1.removeView(Answer);
                MyCameraPreview = null;
                Daltonize();
            }
        });
        t1.addView(btnDalt);*/
    }

    private int ANN( double[] ANNinput){
        NeuralNetwork neuralNetwork;
        int intOutput = 0;
        try {
            InputStream is = getAssets().open("ANN.nnet");
            //LOAD NEURAL NETWORK INTO SYSTEM AND CLOSE INPUTSTREAM
            neuralNetwork = NeuralNetwork.load(is);
            is.close();

            //SET INPUTS FOR NEURAL NETWORK
            neuralNetwork.setInput(ANNinput);

            //CALCULATE THE OUTPUT FOR THE ANN
            neuralNetwork.calculate();

            //GET THE OUTPUT OF THE ANN
            double[] networkOutput = neuralNetwork.getOutput();

            //interpretation of output
            networkOutput[0] = (int) Math.round(networkOutput[0]);
            networkOutput[1] = (int) Math.round(networkOutput[1]);
            //tv1.setText(tv1.getText() + String.valueOf(networkOutput[0]) +"x "+ String.valueOf(networkOutput[1]));
            if( networkOutput[0] == 0 && networkOutput[1] == 0){
                intOutput = 1;
            }
            else if( networkOutput[0] == 1 && networkOutput[1] == 0){
                intOutput = 2;
            }
            else if( networkOutput[0] == 0 && networkOutput[1] == 1){
                intOutput = 3;
            }
            else if( networkOutput[0] == 1 && networkOutput[1] == 1){
                intOutput = 4;
            }


        } catch (Exception e) {
            //tv1.setText( tv1.getText() + e.toString());
        }
        return intOutput;
    }

    @SuppressLint("ResourceType")
    protected void Daltonize(){
        // Create Camera Preview
//        MyCameraPreview = new ImageView(this);
//        final LinearLayout lin = (LinearLayout) findViewById(R.id.linCam);
        RelativeLayout frame = (RelativeLayout) findViewById(R.id.frame);
        frame.setVisibility(View.VISIBLE);

        LinearLayout lin = (LinearLayout) findViewById(R.id.linCam);
        lin.setVisibility(View.VISIBLE);

        MyCameraPreview = new ImageView(this);
        final SurfaceView camView = new SurfaceView(this);
        final SurfaceHolder camHolder = camView.getHolder();

        camView.setId(9);

        camPreview = new CameraPreview(PreviewSizeWidth, PreviewSizeHeight, MyCameraPreview, intDaltType);

        camHolder.addCallback(camPreview);
//        camHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

//        RelativeLayout.LayoutParams parambaliw = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

//        mainLayout = (RelativeLayout) findViewById(R.id.relativeLayout1);
//        mainLayout.addView(camView, new LayoutParams(1440, 1080));
//        mainLayout.addView(MyCameraPreview, new LayoutParams(1440, 1080));
//        mainLayout.addView(camView, new LayoutParams(PreviewSizeWidth, PreviewSizeHeight));
//        mainLayout.addView(MyCameraPreview, new LayoutParams(PreviewSizeWidth, PreviewSizeHeight));
        frame.addView(camView, new LayoutParams(1, 1));

//        parambaliw.setMargins(0,0,0,0);
//        parambaliw.addRule(RelativeLayout.CENTER_VERTICAL);
//        mainLayout.addView(MyCameraPreview, new LayoutParams(1080, 1440));
//        mainLayout.addView(MyCameraPreview, parambaliw);

//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        Button btnLow = (Button) findViewById(R.id.Low);
        Button btnMed = (Button) findViewById(R.id.Medium);
        Button btnHigh = (Button) findViewById(R.id.High);
        Button btnClose = (Button) findViewById(R.id.Close);

        btnLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeDaltonizeViews();
                PreviewSizeWidth = 240;
                PreviewSizeHeight = 320;
                Daltonize();
            }
        });
        btnMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeDaltonizeViews();
                PreviewSizeWidth = 480;
                PreviewSizeHeight = 640;
                Daltonize();
            }
        });
        btnHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeDaltonizeViews();
                PreviewSizeWidth = 960;
                PreviewSizeHeight = 1280;
                Daltonize();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeDaltonizeViews();
                PreviewSizeWidth = 480;
                PreviewSizeHeight = 640;
                intCtr = -1;
                welcome();
            }
        });
//        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        mainLayout.addView(btnClose,lp);
//        lin.addView(MyCameraPreview, new LayoutParams(PreviewSizeWidth, PreviewSizeHeight));
//        frame.addView(MyCameraPreview, new LayoutParams(1080, 1440));
        frame.addView(MyCameraPreview, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    @Override   // Toolbar Back Button
    @SuppressLint("ResourceType")
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                Button b1 = (Button) findViewById(1);
                LinearLayout startLayout = (LinearLayout) findViewById(R.id.linStart);
                LinearLayout recordsLayout = (LinearLayout) findViewById(R.id.linearLayout3);
                if (recordsLayout.getVisibility() == View.VISIBLE){
                    recordsLayout.setVisibility(View.GONE);
                    getSupportActionBar().hide();
                    welcome();
                }
                else if (startLayout.getVisibility() == View.VISIBLE){
                    startLayout.setVisibility(View.GONE);
                    getSupportActionBar().hide();
                    welcome();
                }
                // Popup would be triggered if toolbar back button is clicked during test
                else if (b1.getVisibility() == View.VISIBLE) {
                    confirmationPrompt();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("ResourceType")
    public void confirmationPrompt(){
        new AlertDialog.Builder(this)
                .setTitle("Return to Main Menu")
                .setMessage("Are you sure you want to go back to menu? Note that your current progress in the test will not be saved.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        restart();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    //    @Override   // Back Button
//    @SuppressLint("ResourceType")
//    public void onBackPressed(){
//        Button B1 = (Button) findViewById(1);
//        // Popup would only be triggered if back button / toolbar back button is clicked during the test
//        if (B1.getVisibility() == View.VISIBLE) {
//            confirmationPrompt();
//        } else {
//            Daltonization.super.onBackPressed();
//            this.finishAffinity();
//            System.exit(0);
//        }
//    }
    @Override   // Back Button
//    @SuppressLint("ResourceType")
    public void onBackPressed(){
//        Button B1 = (Button) findViewById(1);
//        // Popup would only be triggered if back button / toolbar back button is clicked during the test
//        if (B1.getVisibility() == View.VISIBLE) {
//            confirmationPrompt();
//        } else
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back once more to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void restart(){
        RelativeLayout rel = (RelativeLayout) findViewById(R.id.relativeLayout1);
        rel.setBackgroundColor(Color.rgb(255,200,0));

        removeQuestionnaireViews();
        gifTitle.setVisibility(View.VISIBLE);
        getSupportActionBar().hide();
        intCtr = -1;

        welcome();
    }

    @SuppressLint("ResourceType")
    public void removeMainViews(){
        RelativeLayout rel = (RelativeLayout) findViewById(R.id.relativeLayout1);
        LinearLayout lin = (LinearLayout) findViewById(R.id.linearLayout1);

        Start = (Button) findViewById(0);
        Records = (Button) findViewById(7);
        About = (Button) findViewById(8);

        lin.removeView(Start);
        lin.removeView(Records);
        lin.removeView(About);

        gifTitle.setVisibility(View.GONE);
        rel.setBackgroundColor(Color.WHITE);
    }

    public void removeQuestionnaireViews(){
        LinearLayout lin = (LinearLayout) findViewById(R.id.linearLayout2);
        lin.removeAllViews();
    }

    public void removeStartViews(){
        LinearLayout lin = (LinearLayout) findViewById(R.id.linStart);
        lin.setVisibility(View.GONE);
    }

    @SuppressLint("ResourceType")
    public void removeDaltonizeViews(){
//        mainLayout = (RelativeLayout) findViewById(R.id.relativeLayout1);
        RelativeLayout frame = (RelativeLayout) findViewById(R.id.frame);
        LinearLayout lin = (LinearLayout) findViewById(R.id.linCam);
        SurfaceView camView = (SurfaceView) findViewById(9);
        SurfaceHolder camHolder = camView.getHolder();

        frame.removeView(camView);
        frame.removeView(MyCameraPreview);
        camHolder.removeCallback(camPreview);
        lin.setVisibility(View.GONE);
    }

    protected void onPause(){
        if ( camPreview != null)
            camPreview.onPause();
        super.onPause();


    }

    public String getList(){
        String text = "";

        try{
            InputStream file = getAssets().open("Records.txt");
            int size = file.available();
            byte[] buffer = new byte[size];
            file.read(buffer);
            file.close();
            text = new String(buffer);
        }
        catch (IOException ex){
            ex.printStackTrace();

        }
        return text;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
}

