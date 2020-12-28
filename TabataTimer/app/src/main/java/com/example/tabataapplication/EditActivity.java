package com.example.tabataapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabataapplication.Adapters.EditDataAdapter;
import com.example.tabataapplication.DatabaseHelper.DatabaseAdapter;
import com.example.tabataapplication.ItemTouchHelper.SimpleItemTouchHelperCallback;
import com.example.tabataapplication.Models.Phase;
import com.example.tabataapplication.Models.Sequence;
import com.example.tabataapplication.databinding.ActivityEditBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import yuku.ambilwarna.AmbilWarnaDialog;

public class EditActivity extends AppCompatActivity {
    ActivityEditBinding binding;
    boolean isRotate = false;
    List<Phase> list = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    DatabaseAdapter databaseAdapter;
    EditDataAdapter editDataAdapter;
    private Sequence currentSequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        setSupportActionBar(binding.toolbar);

        Intent intent = getIntent();
        final int idSeq = intent.getIntExtra("idSeq", 0);
        databaseAdapter = new DatabaseAdapter(EditActivity.this);
        databaseAdapter.open();
        currentSequence = databaseAdapter.getSequence(idSeq);
        list = databaseAdapter.getPhasesOfSequence(idSeq);

        layoutManager = new LinearLayoutManager(this);
        binding.editList.setLayoutManager(layoutManager);
        editDataAdapter = new EditDataAdapter(this, list);
        binding.editList.setAdapter(editDataAdapter);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(editDataAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(binding.editList);

        LocalBroadcastManager.getInstance(this).registerReceiver(phaseReceiver,
                new IntentFilter("phase-changed"));

        binding.create.setOnClickListener(v -> {
            Intent intent1 = new Intent(EditActivity.this, MainActivity.class);
            if (list.size() != 0) {
                databaseAdapter.close();
                startActivity(intent1);
            } else {
                Toast.makeText(v.getContext(), "Add at least one phase", Toast.LENGTH_LONG).show();
            }
        });

        binding.fabCreate.setOnClickListener(v -> animation(v));
        binding.fabPrep.setOnClickListener(v -> {
            Phase newPhase = new Phase(idSeq, Action.PREPARATION, getResources().getDrawable(R.drawable.ic_preparation_color), 5, "");
            list.add(newPhase);
            databaseAdapter.insertPhase(newPhase);
            editDataAdapter.notifyDataSetChanged();
            animation(v);
        });
        binding.fabWork.setOnClickListener((View.OnClickListener) v -> {
            Phase newPhase = new Phase((int) idSeq, Action.WORK, getResources().getDrawable(R.drawable.ic_work_color), 10, "");
            list.add(newPhase);
            databaseAdapter.insertPhase(newPhase);
            editDataAdapter.notifyDataSetChanged();
            animation(v);
        });
        binding.fabRelax.setOnClickListener((View.OnClickListener) v -> {
            Phase newPhase = new Phase((int) idSeq, Action.RELAX, getResources().getDrawable(R.drawable.ic_relax_color), 3, "");
            list.add(newPhase);
            databaseAdapter.insertPhase(newPhase);
            editDataAdapter.notifyDataSetChanged();
            animation(v);
        });
        binding.fabRelaxBetweenSets.setOnClickListener((View.OnClickListener) v -> {
            Phase newPhase = new Phase((int) idSeq, Action.RELAX_BETWEEN_SETS, getResources().getDrawable(R.drawable.ic_relax_between_sets_color), 2, "");
            list.add(newPhase);
            databaseAdapter.insertPhase(newPhase);
            editDataAdapter.notifyDataSetChanged();
            animation(v);
        });
        ViewAnimation.init(binding.fabPrep);
        ViewAnimation.init(binding.fabWork);
        ViewAnimation.init(binding.fabRelax);
        ViewAnimation.init(binding.fabRelaxBetweenSets);
    }

    public BroadcastReceiver phaseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            assert extras != null;
            ActionBroadcast action = (ActionBroadcast) extras.getSerializable("action");
            int phasePosition = extras.getInt("phase-id");

            switch (Objects.requireNonNull(action)) {
                case CHANGE: {
                    Phase phaseToChange = list.get(phasePosition);
                    databaseAdapter.updatePhase(phaseToChange);
                }
                break;
                case DELETE: {
                    Phase phaseToDelete = list.get(phasePosition);
                    databaseAdapter.deletePhase(phaseToDelete.getId());
                }
                break;
            }
        }
    };

    private void animation(View v) {
        isRotate = ViewAnimation.rotateFab(v, !isRotate);
        if (isRotate) {
            showIn();
        } else {
            showOut();
        }
    }

    private void showIn() {
        ViewAnimation.showIn(binding.fabPrep);
        ViewAnimation.showIn(binding.fabWork);
        ViewAnimation.showIn(binding.fabRelax);
        ViewAnimation.showIn(binding.fabRelaxBetweenSets);
    }

    private void showOut() {
        ViewAnimation.showOut(binding.fabPrep);
        ViewAnimation.showOut(binding.fabWork);
        ViewAnimation.showOut(binding.fabRelax);
        ViewAnimation.showOut(binding.fabRelaxBetweenSets);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addSets) {
            dialog(MenuAction.SETS);
        } else if (item.getItemId() == R.id.editTitle) {
            dialog(MenuAction.TITLE);
        } else if (item.getItemId() == R.id.chooseColor) {
            openColourPicker();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    public void dialog(MenuAction action) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptsView = layoutInflater.inflate(R.layout.dialog_sets, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);
        mDialogBuilder.setView(promptsView);
        final EditText userInput = promptsView.findViewById(R.id.input_text);
        final TextView textView = promptsView.findViewById(R.id.tv);
        switch (action) {
            case SETS: {
                userInput.setText(String.valueOf(currentSequence.getSetsAmount()));
                textView.setText(R.string.enter_amount_of_sets);
                userInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        currentSequence.setSetsAmount(Integer.parseInt(userInput.getText().toString()));
                                        databaseAdapter.updateSequence(currentSequence);
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
            }
            break;
            case TITLE: {
                userInput.setText(String.valueOf(currentSequence.getTitle()));
                textView.setText(R.string.enter_new_sequence_title);
                userInput.setInputType(InputType.TYPE_CLASS_TEXT);
                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        currentSequence.setTitle(userInput.getText().toString());
                                        databaseAdapter.updateSequence(currentSequence);
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
            }
            break;
        }
        AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.show();
    }

    public void openColourPicker() {
        int prevColour = currentSequence.getColour();
        AmbilWarnaDialog colourPicker = new AmbilWarnaDialog(this, prevColour, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                currentSequence.setColour(color);
                databaseAdapter.updateSequence(currentSequence);
            }
        });
        colourPicker.show();
    }

    public enum MenuAction {TITLE, SETS}
}