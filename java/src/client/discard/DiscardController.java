package client.discard;

import shared.definitions.*;
import client.base.*;
import client.misc.*;
import client.model.*;
import client.model.player.*;
import shared.commands.*;
import shared.deserializers.*;
import com.google.gson.*;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController {
    private GameManager manager = GameManager.getInstance();
    private ResourceList temp = new ResourceList();
    private ResourceList resources;
    private int discardAmount;
    private PlayerIndex index;
    private Player player;

	private IWaitView waitView;
	
	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {
		
		super(view);
		
		this.waitView = waitView;
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
        temp.increase(resource);
        getDiscardView().setResourceDiscardAmount(resource, temp.count(resource));
        if (temp.total() == this.discardAmount){
            getDiscardView().setDiscardButtonEnabled(true); 
        }
        else {
            getDiscardView().setDiscardButtonEnabled(false); 
        }
        if (temp.count(resource) == this.resources.count(resource)){
                getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
        }
        update();
        System.out.println("Increase");
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
        temp.decrease(resource);
        getDiscardView().setResourceDiscardAmount(resource, temp.count(resource));
        if (temp.count(resource) == 0) {
            getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
        }
        else {
            getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
        }
        update();
        System.out.println("Decrease");
		
	}

	@Override
	public void discard() {
        this.manager.getServer().execute(new DiscardCards(index, temp));	
        getDiscardView().closeModal();
	}

    public void update(){
        if (this.temp.total() == this.discardAmount){
            getDiscardView().setDiscardButtonEnabled(true); 
        }
        else {
            getDiscardView().setDiscardButtonEnabled(false); 
        }
        for (ResourceType r: ResourceType.values()){
            boolean discard = temp.count(r) > 0;
            getDiscardView().setResourceMaxAmount(r, this.resources.count(r));
            if (resources.count(r) > 0) {
                getDiscardView().setResourceAmountChangeEnabled(r, true, discard);
            }
            if (this.temp.count(r) == this.resources.count(r) || this.temp.total() == this.discardAmount){
                getDiscardView().setResourceAmountChangeEnabled(r, false, discard);
            }
        }
        getDiscardView().setStateMessage(temp.total() + "/" + this.discardAmount);
    }

    public void start(PlayerIndex index){
        this.index = index;
        this.player = this.manager.getActiveGame().getPlayer(index);
        this.resources = this.player.getResources();
        for (ResourceType r: ResourceType.values()){
            System.out.println("Player has: " + r.getValue() + " " + resources.count(r));
        }
        
        this.discardAmount = this.resources.total()/2;
        update();
        getDiscardView().showModal();
    }
}
