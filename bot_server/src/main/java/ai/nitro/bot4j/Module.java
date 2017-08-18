package ai.nitro.bot4j;

import com.google.inject.AbstractModule;

import ai.nitro.bot4j.bot.Bot;
import com.google.inject.multibindings.MapBinder;

public class Module extends AbstractModule {

	@Override
	protected void configure() {
		install(new Bot4jModule());

		final MapBinder<Integer, Bot> botBinder = MapBinder.newMapBinder(binder(),
				Integer.class, Bot.class);

	}

}