package com.example.tema2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tema2.models.Mark;

import java.util.List;

import listeners.DeleteListener;
import listeners.GetMarksListener;
import listeners.InsertMarkListener;

public class MainActivity extends AppCompatActivity {

    private MarkRepository markRepository;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        markRepository = new MarkRepository(getApplicationContext());

        setContentView(R.layout.activity_main);

        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "mark")
                .allowMainThreadQueries()
                .build();

        Button addButton = (Button) findViewById(R.id.addButton);
        Button removeButton = (Button) findViewById(R.id.removeButton);

        final TextView textView = (TextView) findViewById(R.id.textView);

        final EditText nameInput = (EditText) findViewById(R.id.nameInput);
        final EditText markInput = (EditText) findViewById(R.id.markInput);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Mark mark = new Mark();
                String markText = markInput.getText().toString();

                mark.name = nameInput.getText().toString();

                if(mark.name.isEmpty() || markText.isEmpty()){
                    Toast.makeText(MainActivity.this, "You must add a name and a mark!", Toast.LENGTH_LONG).show();
                    return;
                }

                mark.mark = Integer.parseInt(markText);

                markRepository.insertMarkAsync(mark, new InsertMarkListener() {
                    @Override
                    public void actionSuccess() {
                        Toast.makeText(MainActivity.this, "Added: " + mark.name, Toast.LENGTH_SHORT).show();
                        getData();
                    }

                    @Override
                    public void actionFailed(){
                        textView.setText("ERROR");
                    }
                });
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameInput.getText().toString();
                markRepository.deleteMarkAsync(name, new DeleteListener(){
                    @Override
                    public void deleteResult (Boolean status) {
                        if(status){
                            Toast.makeText(MainActivity.this, "Removed: " + name, Toast.LENGTH_SHORT).show();
                            getData();
                        }

                        Toast.makeText(MainActivity.this, "Not found:  " + name, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        initRecyclerView();
    }

    private void getData(){
        final MainActivity oldThis = this;
        markRepository.getMarksAsync(new GetMarksListener() {
            @Override
            public void actionSuccess(List<Mark> marks) {
                MarksDisplayAdapter adapter = new MarksDisplayAdapter(oldThis, marks);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        getData();
    }
}
