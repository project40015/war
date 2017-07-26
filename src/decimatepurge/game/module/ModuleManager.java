package decimatepurge.game.module;

import java.util.ArrayList;
import java.util.List;

import decimatepurge.core.Manager;
import decimatepurge.game.module.modules.BlockAnimalSpawnModule;
import decimatepurge.game.module.modules.BlockCommandModule;
import decimatepurge.game.module.modules.BowHitDamageIndicatorModule;
import decimatepurge.game.module.modules.CompassPointModule;
import decimatepurge.game.module.modules.DeathMessageModule;
import decimatepurge.game.module.modules.DefaultChatModule;
import decimatepurge.game.module.modules.EnderpearlDamageRemovalModule;
import decimatepurge.game.module.modules.GoldenHeadModule;
import decimatepurge.game.module.modules.GriefProtectionModule;
import decimatepurge.game.module.modules.JoinServerModule;
import decimatepurge.game.module.modules.MapModule;
import decimatepurge.game.module.modules.NoConnectionModule;
import decimatepurge.game.module.modules.NoDamageModule;
import decimatepurge.game.module.modules.NoRegenerationModule;
import decimatepurge.game.module.modules.NoWeatherModule;
import decimatepurge.game.module.modules.QuitServerModule;
import decimatepurge.game.module.modules.RegularScoreboardInformationModule;
import decimatepurge.game.module.modules.SpawnGearModule;
import decimatepurge.game.module.modules.TeamModule;
import decimatepurge.game.module.modules.commands.FactionCommandModule;
import decimatepurge.game.module.modules.commands.HealthCommandModule;
import decimatepurge.game.module.modules.commands.HubCommandModule;
import decimatepurge.game.module.modules.commands.ShoutCommandModule;
import decimatepurge.game.module.modules.commands.SpectateCommandModule;
import decimatepurge.game.module.modules.commands.StartGameCommandModule;

public class ModuleManager implements Manager {

	public enum ModuleID {

		// Commands
		START_GAME_COMMAND_MODULE, FACTION_DEFAULT_COMMAND_MODULE, SHOUT_COMMAND_MODULE, SPECTATE_COMMAND_MODULE, HUB_COMMAND_MODULE, HEALTH_COMMAND_MODULE,

		// Game mechanics
		NO_DAMAGE_MODULE, GRIEF_PROTECTION_MODULE, NO_CONNECTION_MODULE, TEAM_MODULE, DEFAULT_CHAT_MODULE, JOIN_SERVER_MODULE, QUIT_SERVER_MODULE, MAP_MODULE, NO_REGENERATION_MODULE, REGULAR_SCOREBOARD_INFORMATION_MODULE, BLOCK_COMMAND_MODULE, DEATH_MESSAGE_MODULE, COMPASS_POINT_MODULE, SPAWN_GEAR_MODULE, ENDERPEARL_DAMAGE_REMOVAL_MODULE, BLOCK_ANIMAL_SPAWN_MODULE, BOW_HIT_DAMAGE_INDICATOR_MODULE, GOLDEN_HEAD_MODULE, NO_WEATHER_MODULE;
	}

	private List<Module> modules = new ArrayList<>();

	public ModuleManager() {
		fillModules();
	}

	private void fillModules() {
		this.modules.add(new NoDamageModule(ModuleID.NO_DAMAGE_MODULE));
		this.modules.add(new GriefProtectionModule(
				ModuleID.GRIEF_PROTECTION_MODULE, ModuleID.NO_DAMAGE_MODULE));
		this.modules.add(new NoConnectionModule(ModuleID.NO_CONNECTION_MODULE));
		this.modules.add(new TeamModule(ModuleID.TEAM_MODULE));
		this.modules.add(new DefaultChatModule(ModuleID.DEFAULT_CHAT_MODULE));
		this.modules.add(new JoinServerModule(ModuleID.JOIN_SERVER_MODULE));
		this.modules.add(new QuitServerModule(ModuleID.QUIT_SERVER_MODULE));
		this.modules.add(new MapModule(ModuleID.MAP_MODULE));
		this.modules.add(new NoRegenerationModule(
				ModuleID.NO_REGENERATION_MODULE));
		this.modules.add(new RegularScoreboardInformationModule(
				ModuleID.REGULAR_SCOREBOARD_INFORMATION_MODULE));
		this.modules.add(new StartGameCommandModule(
				ModuleID.START_GAME_COMMAND_MODULE, "forcestart"));
		this.modules.add(new FactionCommandModule(
				ModuleID.FACTION_DEFAULT_COMMAND_MODULE, "factions"));
		this.modules.add(new ShoutCommandModule(ModuleID.SHOUT_COMMAND_MODULE,
				"shout"));
		this.modules.add(new BlockCommandModule(ModuleID.BLOCK_COMMAND_MODULE));
		this.modules.add(new DeathMessageModule(ModuleID.DEATH_MESSAGE_MODULE));
		this.modules.add(new CompassPointModule(ModuleID.COMPASS_POINT_MODULE));
		this.modules.add(new SpectateCommandModule(
				ModuleID.SPECTATE_COMMAND_MODULE, "spectate"));
		this.modules.add(new HubCommandModule(ModuleID.HUB_COMMAND_MODULE,
				"hub"));
		this.modules.add(new SpawnGearModule(ModuleID.SPAWN_GEAR_MODULE));
		this.modules.add(new HealthCommandModule(
				ModuleID.HEALTH_COMMAND_MODULE, "health"));
		this.modules.add(new BowHitDamageIndicatorModule(
				ModuleID.BOW_HIT_DAMAGE_INDICATOR_MODULE));
		this.modules.add(new EnderpearlDamageRemovalModule(
				ModuleID.ENDERPEARL_DAMAGE_REMOVAL_MODULE));
		this.modules.add(new BlockAnimalSpawnModule(
				ModuleID.BLOCK_ANIMAL_SPAWN_MODULE));
		this.modules.add(new GoldenHeadModule(ModuleID.GOLDEN_HEAD_MODULE));
		this.modules.add(new NoWeatherModule(ModuleID.NO_WEATHER_MODULE));
	}

	public Module getModule(ModuleID id) {
		for (Module module : modules) {
			if (module.getModuleId() == id) {
				return module;
			}
		}
		return null;
	}

	@Override
	public void onDisable() {

	}

}
