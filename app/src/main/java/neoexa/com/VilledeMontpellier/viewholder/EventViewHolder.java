package neoexa.com.VilledeMontpellier.viewholder;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.xml.parsers.FactoryConfigurationError;

import neoexa.com.VilledeMontpellier.Model.Event;
import neoexa.com.VilledeMontpellier.R;

public class EventViewHolder extends RecyclerView.ViewHolder{

    private TextView textViewTitle;
    private TextView textViewDesc;
    private TextView textViewDateBegin;
    private TextView textViewDateEnd;
    private TextView textViewAddress;

    private Button fabAddToCalendar;

    //itemView est la vue correspondante à 1 cellule
    public EventViewHolder(View itemView) {
        super(itemView);


        textViewTitle = (TextView) itemView.findViewById(R.id.card_event_title);
        textViewDesc = (TextView) itemView.findViewById(R.id.card_event_description);
        textViewDateBegin = (TextView) itemView.findViewById(R.id.card_event_begin);
        textViewDateEnd = (TextView) itemView.findViewById(R.id.card_event_end);
        textViewAddress = (TextView) itemView.findViewById(R.id.card_event_address);
        fabAddToCalendar = (Button) itemView.findViewById(R.id.card_add_calendar_btn);

    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bindToEvent(Event event, View.OnClickListener addCalendarClickListner){
        String begin = event.begin.getDay()+"/"+event.begin.getMonth()+"/"+event.begin.getYear();
        String end = event.end.getDay()+"/"+event.end.getMonth()+"/"+event.end.getYear();
        textViewTitle.setText(event.title);
        textViewDesc.setText(event.body);
        textViewDateBegin.setText(begin);
        textViewDateEnd.setText(end);
        textViewAddress.setText(event.address);

        fabAddToCalendar.setOnClickListener(addCalendarClickListner);

    }
}
