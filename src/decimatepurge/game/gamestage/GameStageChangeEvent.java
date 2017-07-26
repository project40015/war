package decimatepurge.game.gamestage;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import decimatepurge.game.GameStage;

public class GameStageChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
	private GameStage toStage;
    
    public GameStageChangeEvent(GameStage toStage){
    	this.toStage = toStage;
    }
    
    public GameStage getToStage(){
    	return this.toStage;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
	
}
