/*
 * Copyright 2008-2014 Red Hat, Inc, and individual contributors.
 * 
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 * 
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.immutant.messaging.processors;

import org.immutant.core.as.CoreServices;
import org.immutant.core.processors.RegisteringProcessor;
import org.immutant.messaging.Destinationizer;
import org.immutant.messaging.MessageProcessorGroupizer;
import org.immutant.messaging.as.MessagingServices;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.msc.service.ServiceController.Mode;
import org.jboss.msc.service.ServiceTarget;
import org.projectodd.shimdandy.ClojureRuntimeShim;


public class DestinationizerInstaller extends RegisteringProcessor {

    public DestinationizerInstaller(ServiceTarget globalTarget) {
        this.globalTarget = globalTarget;
    }
    
    public RegistryEntry registryEntry(DeploymentPhaseContext context) {
        DeploymentUnit unit = context.getDeploymentUnit();
                
        Destinationizer service = new Destinationizer(unit, this.globalTarget);
                
        context.getServiceTarget().addService(MessagingServices.destinationizer( unit ), service)
            .addDependency( CoreServices.runtime( context.getDeploymentUnit() ), 
                            ClojureRuntimeShim.class,
                            service.getClojureRuntimeInjector()  )
            .addDependency( MessagingServices.messageProcessorGroupizer( context.getDeploymentUnit() ),
                            MessageProcessorGroupizer.class,
                            service.getMessageProcessorGroupizerInjector() )
            .setInitialMode(Mode.ACTIVE)
            .install();
        
        return new RegistryEntry( "destinationizer", service );
    }

    private ServiceTarget globalTarget;
}
