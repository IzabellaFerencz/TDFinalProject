package controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import model.Event;
import model.EventModel;
import model.User;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.ResourceBundle;
import java.net.URL;

public class EventsController extends BaseController implements Initializable
{
	@FXML
	private TableView<EventModel> events;
	@FXML
	private Label username;
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
	@FXML
	private TableColumn<EventModel, String> statusCell;
	
	private ObservableList<EventModel> eventsModels;
	
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
		  loadEvents();
		  addAvailableButtonToTable();
		  addUnavailableButtonToTable();
	}
	
	@FXML 
    protected void handleLogOut(ActionEvent event) 
    {
		User.setUser(null);
		redirect(event, "../fxml/LogInPage.fxml", 400, 400);
    }
	
	@FXML 
    protected void getUsersInvites(ActionEvent event) 
    {
		redirect(event, "../fxml/InviteListPage.fxml", 800, 600);
    }
	
	@FXML 
    protected void getUsersNotifications(ActionEvent event) 
    {
		redirect(event, "../fxml/UserNotifications.fxml", 800, 600);
    }
	
	@FXML
	public void getEvents(ActionEvent event) throws ParseException 
	{
		loadEvents();
		
	}
	
	private void addAvailableButtonToTable() 
	{
        TableColumn<EventModel, Void> colBtn = new TableColumn("Available");

        Callback<TableColumn<EventModel, Void>, TableCell<EventModel, Void>> cellFactory = new Callback<TableColumn<EventModel, Void>, TableCell<EventModel, Void>>() 
        {
        	@Override
            public TableCell<EventModel, Void> call(final TableColumn<EventModel, Void> param)
        	{ 
        		
                final TableCell<EventModel, Void> cell = new TableCell<EventModel, Void>() 
                {                	
                    private final Button btn = new Button("Set Available");
                    {
                        btn.setOnAction((ActionEvent event) -> 
                        {
                        	EventModel data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                            Gson gson = new Gson();
                            Event eventObj = new Event();
                            eventObj.setIdEvent(data.getId());
                            if(getAvailability(eventObj))
                            {
                            	message.setText("You are already set as available!");
                            }
                            else
                            {
                        		Map<String, Object> eventPartMap = new HashMap<String, Object>();
                        		eventPartMap.put("participant", User.getUser());
                        		eventPartMap.put("event", eventObj);
                                String resp = sendToServer("newEventsParticipant", gson.toJson(eventPartMap));
                                if(resp.compareTo("Fail")==0)
                                {
                                	message.setText("Failed to set as available!");
                                }
                                else
                                {
                                	loadEvents();
                                }
                            }
                            
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
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        events.getColumns().add(colBtn);

    }
	
	private void addUnavailableButtonToTable() 
	{
        TableColumn<EventModel, Void> colBtn = new TableColumn("Not Available");

        Callback<TableColumn<EventModel, Void>, TableCell<EventModel, Void>> cellFactory = new Callback<TableColumn<EventModel, Void>, TableCell<EventModel, Void>>() 
        {
        	@Override
            public TableCell<EventModel, Void> call(final TableColumn<EventModel, Void> param)
        	{ 
        		
                final TableCell<EventModel, Void> cell = new TableCell<EventModel, Void>() 
                {                	
                    private final Button btn = new Button("Set Unavailable");
                    {
                        btn.setOnAction((ActionEvent event) -> 
                        {
                        	EventModel data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                            Gson gson = new Gson();
                            Event eventObj = new Event();
                            eventObj.setIdEvent(data.getId());
                            if(getAvailability(eventObj))
                            {
                            	Map<String, Object> eventPartMap = new HashMap<String, Object>();
                        		eventPartMap.put("participant", User.getUser());
                        		eventPartMap.put("event", eventObj);
                                String resp = sendToServer("removeEventsParticipant", gson.toJson(eventPartMap));
                                if(resp.compareTo("Fail")==0)
                                {
                                	message.setText("Failed to set as unavailable!");
                                }
                                else
                                {
                                	loadEvents();
                                }
                            	
                            }
                            else
                            {
                            	message.setText("You are already set as unavailable!");
                            }
                            
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
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        events.getColumns().add(colBtn);

    }
	
	private boolean getAvailability(Event event) 
	{
		Gson gson = new Gson();
		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put("participant", User.getUser());
		statusMap.put("event", event);
		String statusResponse = sendToServer("getEventsParticipant", gson.toJson(statusMap));
		if(statusResponse.compareTo("Fail") == 0)
		{
			return false;
		}
		
		return true;
	}
	
	private void loadEvents()
	{
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		String serverResponse = sendToServer("getEvents", map);
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
				String av;
				if(getAvailability(eventObj))
				{
					av = "Available";
				}
				else
				{
					av="Not Available";
				}
				eventList.add(new EventModel(eventObj.getIdEvent(),eventObj.getName(), eventObj.getLocation(), eventObj.getDatetime(), eventObj.getNrOfSeats(), eventObj.getUser().getUsername(), av));
			}
			this.eventsModels = FXCollections.observableList(eventList);
			idCell.setCellValueFactory(new PropertyValueFactory<>("Id"));
			nameCell.setCellValueFactory(new PropertyValueFactory<>("Name"));
			locationCell.setCellValueFactory(new PropertyValueFactory<>("Location"));
			dateCell.setCellValueFactory(new PropertyValueFactory<>("Datetime"));
			seatsCell.setCellValueFactory(new PropertyValueFactory<>("NrOfSeats"));
			orgCell.setCellValueFactory(new PropertyValueFactory<>("Organizer"));
			statusCell.setCellValueFactory(new PropertyValueFactory<>("Status"));
			events.setItems(this.eventsModels);
		}
	}
}
