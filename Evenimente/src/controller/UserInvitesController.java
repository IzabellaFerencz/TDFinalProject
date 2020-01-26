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
import model.EventModel;
import model.Invitation;
import model.InvitationModel;
import model.User;

public class UserInvitesController extends BaseController implements Initializable
{
	@FXML
	private Label username;
	@FXML
	private Label message;
	@FXML
	private TableView<InvitationModel> invites;
	@FXML
	private TableColumn<InvitationModel, Integer> idCell;
	@FXML
	private TableColumn<InvitationModel, String> eventCell;
	@FXML
	private TableColumn<InvitationModel, String> fileCell;
	@FXML
	private TableColumn<InvitationModel, String> secretCodeCell;
	@FXML
	private TableColumn<InvitationModel, String> statusCell;
	
	private ObservableList<InvitationModel> inviteModels;
	
	@FXML 
    protected void handleLogOut(ActionEvent event) 
    {
		User.setUser(null);
		redirect(event, "../fxml/LogInPage.fxml", 400, 400);
    }
	
	@FXML 
    protected void handleBack(ActionEvent event) 
    {
		redirect(event, "../fxml/EventListPage.fxml", 900, 600);
    }
	
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
		  loadInvites();
		  addReserveButtonToTable();
	}
	
	private void loadInvites()
	{
		Gson gson = new Gson();
		String serverResponse = sendToServer("getInvitesOfUser", gson.toJson(User.getUser()));
		if (serverResponse.compareTo("Fail") == 0) 
		{
			System.out.println("Failed to get events");
		} 
		else 
		{
			Type eventListType = new TypeToken<ArrayList<Invitation>>() {}.getType();
			ArrayList<Invitation> listw = gson.fromJson(serverResponse, eventListType);
			ArrayList<InvitationModel> invList = new ArrayList<>();
			for (Invitation invObj : listw)
			{
				String status;
				if(invObj.isAccepted())
				{
					status = "Reserved";
				}
				else
				{
					status = "Waiting";
				}
				invList.add(new InvitationModel(invObj.getIdInvitation(), invObj.getEventParticipant().getEvent().getName(), invObj.getHelpFile(), invObj.getSecretCode(), status));
			}
			this.inviteModels = FXCollections.observableList(invList);
			idCell.setCellValueFactory(new PropertyValueFactory<>("Id"));
			eventCell.setCellValueFactory(new PropertyValueFactory<>("EventName"));
			fileCell.setCellValueFactory(new PropertyValueFactory<>("File"));
			secretCodeCell.setCellValueFactory(new PropertyValueFactory<>("SecretCode"));
			statusCell.setCellValueFactory(new PropertyValueFactory<>("Status"));
			invites.setItems(this.inviteModels);
		}
	}

	private void addReserveButtonToTable() 
	{
        TableColumn<InvitationModel, Void> colBtn = new TableColumn("Reserve");

        Callback<TableColumn<InvitationModel, Void>, TableCell<InvitationModel, Void>> cellFactory = new Callback<TableColumn<InvitationModel, Void>, TableCell<InvitationModel, Void>>() 
        {
        	@Override
            public TableCell<InvitationModel, Void> call(final TableColumn<InvitationModel, Void> param)
        	{ 
        		
                final TableCell<InvitationModel, Void> cell = new TableCell<InvitationModel, Void>() 
                {                	
                    private final Button btn = new Button("Reserve");
                    {
                        btn.setOnAction((ActionEvent event) -> 
                        {
                        	InvitationModel data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                            if(data.getStatus().compareTo("Reserved")==0)
                            {
                            	message.setText("You already reserved!");
                            }
                            else
                            {
                        		Invitation inv = new Invitation();
                        		inv.setIdInvitation(data.getId());
                        		Invitation.setInvitation(inv);
                        		openNewPage(event, "../fxml/ReserveInvitation.fxml", 600, 600);
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

        invites.getColumns().add(colBtn);

    }
}
