package decimatepurge.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Listener;

import decimatepurge.core.Purge;
import decimatepurge.game.module.Module;
import decimatepurge.game.module.ModuleManager;
import decimatepurge.game.module.ModuleManager.ModuleID;

public abstract class GameStage implements Listener {

	private String name;
	private GameStageID id;
	private List<Module> loadedModules = new ArrayList<Module>();
	private ModuleManager moduleManager;

	public GameStage(String name, GameStageID id) {
		this.name = name;
		this.id = id;
		this.moduleManager = Purge.getInstance().getModuleManager();
	}

	public String getName() {
		return name;
	}

	public GameStageID getGameStageID() {
		return this.id;
	}

	public Module getModule(ModuleID id) {
		for (Module module : loadedModules) {
			if (module.getModuleId() == id) {
				return module;
			}
		}
		return null;
	}

	public Module getLoadedModule(ModuleID id) {
		for (Module module : this.loadedModules) {
			if (module.getModuleId().equals(id)) {
				return module;
			}
		}
		return null;
	}

	public boolean isModuleLoaded(ModuleID id) {
		for (Module module : this.loadedModules) {
			if (module.getModuleId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	protected void startNext() {
		Purge.getInstance().getGameStageManager().startNext();
	}

	protected void loadModulesNoArguments(ModuleID... modules) {
		for (ModuleID module : modules) {
			this.loadModule(module);
		}
	}

	protected void loadModule(ModuleID moduleID, Object... objects) {
		Module module = moduleManager.getModule(moduleID);
		this.loadedModules.add(module);
		if (objects != null) {
			module.preload(objects);
		} else {
			module.preload();
		}
	}

	private void replaceModule(Module module){
		for(int i = 0; i < this.loadedModules.size(); i++){
			if(this.loadedModules.get(i).getModuleId().equals(module.getModuleId())){
				this.loadedModules.remove(i);
				return;
			}
		}
		this.loadedModules.add(module);
	}
	
	public void loadGameStage(List<Module> remainingModules) {
		for(Module module : remainingModules){
			replaceModule(module);
		}
		for (Module module : this.loadedModules) {
			module.loadModule();
		}
		start();
	}

	public List<Module> getLoadedModules() {
		return this.loadedModules;
	}

	public abstract void finish(Object... objects);

	public abstract void start();

}
