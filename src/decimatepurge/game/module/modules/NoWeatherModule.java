package decimatepurge.game.module.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;

import decimatepurge.game.module.ModuleManager.ModuleID;

public class NoWeatherModule extends SimpleEventModule {

	public NoWeatherModule(ModuleID id) {
		super(id);
	}
	
	@EventHandler
	public void onChange(WeatherChangeEvent event){
		event.setCancelled(true);
	}

}
