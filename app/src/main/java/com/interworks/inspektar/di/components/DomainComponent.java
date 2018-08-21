package com.interworks.inspektar.di.components;

import com.interworks.inspektar.di.modules.DomainModule;
import com.interworks.inspektar.di.modules.PersistenceModule;
import com.interworks.inspektar.di.scopes.PerService;

import dagger.Component;

@PerService
@Component(dependencies = ApplicationComponent.class, modules = {DomainModule.class})
public interface DomainComponent {
}
