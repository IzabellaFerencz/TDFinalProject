package controller;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Event;
import model.EventParticipant;
import model.ParticipantModel;
import model.User;

public class ParticipantController extends BaseController implements Initializable
{
	@FXML
	private TableView<ParticipantModel> participants;
	@FXML
	private Label username;
	@FXML
	private TableColumn<ParticipantModel, Integer> idCell;
	@FXML
	private TableColumn<ParticipantModel, String> nameCell;

	@FXML
	private TableColumn<ParticipantModel, String> statusCell;
	
	private ObservableList<ParticipantModel> participantsModels;
	
	@FXML
	private Label message;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		  if(User.getUser().getUsername().isEmpty()==true)
		  {     
			  username.setText("Hello, Guest!");
		  }
		  else
		  {
			  username.setText("Hello, " + User.getUser().getUsername() +"!");		
		  }
		  loadParticipants();
		  addInviteButtonToTable();
	}
	
	@FXML 
    protected void handleLogOut(ActionEvent event) 
    {
		User.setUser(null);
		redirect(event, "../fxml/LogInPage.fxml", 400, 400);
    }
	
	@FXML 
    protected void handleBack(ActionEvent event) 
    {
		redirect(event, "../fxml/OrganizerEventListPage.fxml", 1000, 600);
    }
	
	private void loadParticipants()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("idEvent", String.valueOf(Event.getEvent().getIdEvent()));
		Gson gson = new Gson();
		String serverResponse = sendToServer("getParticipants", map);
		if (serverResponse.compareTo("Fail") == 0) 
		{
			System.out.println("Failed to get paticipants");
		} 
		else 
		{
			Type participantListType = new TypeToken<ArrayList<EventParticipant>>() {}.getType();
			ArrayList<EventParticipant> listw = gson.fromJson(serverResponse, participantListType);
			ArrayList<ParticipantModel> participantList = new ArrayList<>();
			for (EventParticipant participant : listw)
			{
				String statusResponse = sendToServer("getParticipantStatus", gson.toJson(participant));
				ParticipantModel model = new ParticipantModel(participant.getIdEventParticipant(), participant.getParticipant().getUsername(), statusResponse);
				participantList.add(model);
			}
			this.participantsModels = FXCollections.observableList(participantList);
			idCell.setCellValueFactory(new PropertyValueFactory<>("Id"));
			nameCell.setCellValueFactory(new PropertyValueFactory<>("Name"));
			statusCell.setCellValueFactory(new PropertyValueFactory<>("Status"));
			participants.setItems(this.participantsModels);
		}
	}
	
	private void addInviteButtonToTable() 
	{
        TableColumn<ParticipantModel, Void> colBtn = new TableColumn("Invite");

        Callback<TableColumn<ParticipantModel, Void>, TableCell<ParticipantModel, Void>> cellFactory = new Callback<TableColumn<ParticipantModel, Void>, TableCell<ParticipantModel, Void>>() 
        {
        	@Override
            public TableCell<ParticipantModel, Void> call(final TableColumn<ParticipantModel, Void> param)
        	{ 
        		
                final TableCell<ParticipantModel, Void> cell = new TableCell<ParticipantModel, Void>() 
                {                	
                    private final Button btnView = new Button("Send Invitation");
                    {
                    	btnView.setOnAction((ActionEvent event) -> 
                        {
                        	Gson gson=new Gson();
                        	ParticipantModel data = getTableView().getItems().get(getIndex());
                            EventParticipant participant = new EventParticipant();
                            participant.setEvent(Event.getEvent());
                            participant.setIdEventParticipant(data.getId());
                            participant.setParticipant(gson.fromJson(sendToServer("getUserByUsername", "{username: \""+data.getName() +"\"}"), User.class));
                            EventParticipant.setEventParticipant(participant);
                            redirect(event, "../fxml/SendInvitation.fxml", 500, 500);
                            
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) 
                    {
                        super.updateItem(item, empty);
                        
                        if (empty) 
                        {
                            setGraphic(null);
                        } 
                        else 
                        {
                            setGraphic(btnView);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        participants.getColumns().add(colBtn);
    }
}
