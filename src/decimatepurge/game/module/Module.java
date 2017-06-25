package decimatepurge.game.module;

import org.bukkit.event.Listener;

import decimatepurge.core.Purge;
import decimatepurge.game.module.ModuleManager.ModuleID;

public abstract class Module implements Listener {

	private ModuleID id;
	private Object[] arguments;
	private ModuleID[] modules;
	private ModuleManager manager;
	
	public Module(ModuleID id, ModuleID... modules){
		this.id = id;
		this.modules = modules;
	}
	
	public ModuleID getModuleId(){
		return id;
	}
	
	protected Object[] getArguments(){
		return arguments;
	}
	
	public void preload(Object...arguments){
		this.arguments = arguments;
	}
	
	public void loadModule(){
		if(manager == null){
			manager = Purge.getInstance().getModuleManager();
		}
		if(modules != null && modules.length != 0){
			for(ModuleID module : modules){
				manager.getModule(module).loadModule();
			}
		}
		load();
	}
	
	public void unloadModule(){
		if(modules != null && modules.length != 0){
			for(ModuleID module : modules){
				manager.getModule(module).unloadModule();
			}
		}
		unload();
	}
	
	protected abstract void load();
	protected abstract void unload();
	
}
