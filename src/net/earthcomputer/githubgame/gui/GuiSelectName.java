package net.earthcomputer.githubgame.gui;

import java.util.Random;

import net.earthcomputer.githubgame.GithubGame;
import net.earthcomputer.githubgame.util.Profiles;

public class GuiSelectName extends GuiScrollable
{
	
	public GuiSelectName(Gui prevGui)
	{
		super(prevGui, 810);
	}
	
	@Override
	public void init()
	{
		super.init();
		
		Random random = new Random();
		for(int i = 0; i < 10; i++)
		{
			buttonList.add(new TextButton(GithubGame.randomGenTitle(random.nextInt()), 20, 20 + i * 80, 500, 50) {
				@Override
				public void onPressed()
				{
					window.setProfile(Profiles.createProfile(getText()));
					window.loadLevel(0);
					window.closeGui();
				}
			});
		}
		staticButtonList.add(new Button("back", 530, 10) {
			@Override
			public void onPressed()
			{
				window.openGui(new GuiMainMenu());
			}
		});
	}
	
}
