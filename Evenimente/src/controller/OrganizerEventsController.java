package controller;

import java.lang.reflect.Type;
import java.net.URL;
import java.text.ParseException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Event;
import model.EventModel;
import model.User;

public class OrganizerEventsController extends BaseController implements Initializable
{
	@FXML
	private TableView<EventModel> events;
	@FXML
	private Label username;
	@FXML
	private Label message;
	@FXML
	private TableColumn<EventModel, Integer> idCell;
	@FXML
	private TableColumn<EventModel, String> nameCell;
	@FXML
	private TableColumn<EventModel, String> locationCell;
	@FXML
	private TableColumn<EventModel, String> dateCell;
	@FXML
	private TableColumn<EventModel, Integer> seatsCell;
	@FXML
	private TableColumn<EventModel, String> orgCell;
	
	private ObservableList<EventModel> eventsModels;
	
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
		  loadEvents();
		  addParticipantButtonToTable();
		  addEditButtonToTable();
		  addDeleteButtonToTable();
	}
	
	@FXML 
    protected void handleLogOut(ActionEvent event) 
    {
		User.setUser(null);
		redirect(event, "../fxml/LogInPage.fxml", 400, 400);
    }
	
	@FXML 
    protected void handleNewEvent(ActionEvent event) 
    {
		Event.setEvent(null);
		redirect(event, "../fxml/NewEvent.fxml", 400, 400);
    }
	
	@FXML
	public void getEvents(ActionEvent event) throws ParseException 
	{
		loadEvents();
	}	
	
	private void loadEvents()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("iduser", String.valueOf(User.getUser().getIdUser()));
		Gson gson = new Gson();
		String serverResponse = sendToServer("getEventsByOrganizer", map);
		if (serverResponse.compareTo("Fail") == 0) 
		{
			System.out.println("Failed to get events");
		} 
		else 
		{
			Type eventListType = new TypeToken<ArrayList<Event>>() {}.getType();
			ArrayList<Event> listw = gson.fromJson(serverResponse, eventListType);
			ArrayList<EventModel> eventList = new ArrayList<>();
			for (Event eventObj : listw)
			{
				eventList.add(new EventModel(eventObj.getIdEvent(),eventObj.getName(), eventObj.getLocation(), eventObj.getDatetime(), eventObj.getNrOfSeats(), eventObj.getUser().getUsername(),""));
			}
			this.eventsModels = FXCollections.observableList(eventList);
			idCell.setCellValueFactory(new PropertyValueFactory<>("Id"));
			nameCell.setCellValueFactory(new PropertyValueFactory<>("Name"));
			locationCell.setCellValueFactory(new PropertyValueFactory<>("Location"));
			dateCell.setCellValueFactory(new PropertyValueFactory<>("Datetime"));
			seatsCell.setCellValueFactory(new PropertyValueFactory<>("NrOfSeats"));
			orgCell.setCellValueFactory(new PropertyValueFactory<>("Organizer"));
			events.setItems(this.eventsModels);
		}
	}
	
	private void addParticipantButtonToTable() 
	{
        TableColumn<EventModel, Void> colBtn = new TableColumn("Participants");

        Callback<TableColumn<EventModel, Void>, TableCell<EventModel, Void>> cellFactory = new Callback<TableColumn<EventModel, Void>, TableCell<EventModel, Void>>() 
        {
        	@Override
            public TableCell<EventModel, Void> call(final TableColumn<EventModel, Void> param)
        	{ 
        		
                final TableCell<EventModel, Void> cell = new TableCell<EventModel, Void>() 
                {                	
                    private final Button btnView = new Button("View Participants");
                    {
                    	btnView.setOnAction((ActionEvent event) -> 
                        {
                        	EventModel data = getTableView().getItems().get(getIndex());
                            Event eventObj = new Event();
                            eventObj.setIdEvent(data.getId());
                            eventObj.setDatetime(data.getDatetime());
                            eventObj.setLocation(data.getLocation());
                            eventObj.setName(data.getName());
                            eventObj.setNrOfSeats(data.getNrOfSeats());
                            Event.setEvent(eventObj);
                        	redirect(event, "../fxml/ParticipantListPage.fxml", 700, 600);      
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

        events.getColumns().add(colBtn);
    }
	
	private void addEditButtonToTable() 
	{
        TableColumn<EventModel, Void> colBtn = new TableColumn("Edit");

        Callback<TableColumn<EventModel, Void>, TableCell<EventModel, Void>> cellFactory = new Callback<TableColumn<EventModel, Void>, TableCell<EventModel, Void>>() 
        {
        	@Override
            public TableCell<EventModel, Void> call(final TableColumn<EventModel, Void> param)
        	{ 
        		
                final TableCell<EventModel, Void> cell = new TableCell<EventModel, Void>() 
                {                	
                    private final Button btnView = new Button("Edit");
                    {
                    	btnView.setOnAction((ActionEvent event) -> 
                        {
                        	EventModel data = getTableView().getItems().get(getIndex());
                            Event eventObj = new Event();
                            eventObj.setIdEvent(data.getId());
                            eventObj.setDatetime(data.getDatetime());
                            eventObj.setLocation(data.getLocation());
                            eventObj.setName(data.getName());
                            eventObj.setNrOfSeats(data.getNrOfSeats());
                            Event.setEvent(eventObj);
                        	redirect(event, "../fxml/EditEvent.fxml", 500, 500);      
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

        events.getColumns().add(colBtn);
    }

	private void addDeleteButtonToTable() 
	{
        TableColumn<EventModel, Void> colBtn = new TableColumn("Delete");

        Callback<TableColumn<EventModel, Void>, TableCell<EventModel, Void>> cellFactory = new Callback<TableColumn<EventModel, Void>, TableCell<EventModel, Void>>() 
        {
        	@Override
            public TableCell<EventModel, Void> call(final TableColumn<EventModel, Void> param)
        	{ 
        		
                final TableCell<EventModel, Void> cell = new TableCell<EventModel, Void>() 
                {                	
                    private final Button btnView = new Button("Delete");
                    {
                    	btnView.setOnAction((ActionEvent event) -> 
                        {
                        	EventModel data = getTableView().getItems().get(getIndex());
                            Event eventObj = new Event();
                            eventObj.setIdEvent(data.getId());
                            eventObj.setDatetime(data.getDatetime());
                            eventObj.setLocation(data.getLocation());
                            eventObj.setName(data.getName());
                            eventObj.setNrOfSeats(data.getNrOfSeats());
                            Event.setEvent(eventObj);
                        	// TODO send to server and delete -> redirect to event list page    
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Event");
                            alert.setContentText("Are you sure you want to delete?");
                            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                            alert.getButtonTypes().setAll(okButton, noButton);
                            alert.showAndWait().ifPresent(type -> {
                                    if (type.getButtonData() == ButtonData.YES) 
                                    {
                                    	Gson gson = new Gson();
                                    	String serverResponse = sendToServer("deleteEvent", gson.toJson(eventObj) );
                                		if (serverResponse.compareTo("Fail")==0)
                                		{
                                				message.setText("Failed to delete event!");
                                		}
                                		else
                                		{
                                			redirect(event, "../fxml/OrganizerEventListPage.fxml", 1000, 600);
                                		}
                                    } 
                                    else 
                                    {
                                    	
                                    }
                            		});
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

        events.getColumns().add(colBtn);
    }
}
