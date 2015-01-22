package am2apidemo;

import am2.api.ArsMagicaApi;
import am2apidemo.proxy.CommonProxy;
import am2apidemo.spell.SpellList;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "am2apidemo", modLanguage="java", name="AM2 API Demo", version = "0.0.1", dependencies = "required-after:arsmagica2")
public class ModCore {

	@Instance(value="am2apidemo")
	public static ModCore instance;

	@SidedProxy(clientSide = "am2apidemo.proxy.ClientProxy", serverSide = "am2apidemo.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	private String compendiumBase;
	private ModItems items;	
	
	public ModCore(){
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		
	}

	@EventHandler
	public void init(FMLInitializationEvent event){		
		items = new ModItems();
		proxy.init();
		proxy.registerEventListeners();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event){		
		proxy.registerPostEventListeners();
	}

	public String getVersion(){
		Mod modclass = this.getClass().getAnnotation(Mod.class);
		return modclass.version();
	}
}
