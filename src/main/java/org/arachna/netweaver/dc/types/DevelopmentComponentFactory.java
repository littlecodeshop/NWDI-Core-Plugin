/**
 *
 */
package org.arachna.netweaver.dc.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory/registry for {@link DevelopmentComponent} objects.
 * 
 * @author Dirk Weigenand
 */
public final class DevelopmentComponentFactory {
    /**
     * maps 'vendor:component.name' to development components.
     */
    private final Map<String, DevelopmentComponent> componentMap = new HashMap<String, DevelopmentComponent>();

    /**
     * Create and register a {@link DevelopmentComponent}.
     * 
     * @param name
     *            the name of the development component to register.
     * @param vendor
     *            the vendor of the development component to register.
     * @param type
     *            the type of this development component.
     * @return the newly created or already registered development component
     */
    public DevelopmentComponent create(final String name, final String vendor, final DevelopmentComponentType type) {
        DevelopmentComponent component = this.get(vendor, name);

        if (null == component) {
            component = new DevelopmentComponent(name, vendor, type);
            this.componentMap.put(this.createComponentKey(name, vendor), component);
        }

        return component;
    }

    /**
     * Create the key for storing a development component in the registry.
     * 
     * @param name
     *            name to use as part of the key.
     * @param vendor
     *            vendor to use as part of the key.
     * @return the concatenation of the given vendor, ':' and the given
     *         component name.
     */
    private String createComponentKey(final String name, final String vendor) {
        return vendor + ":" + name;
    }

    /**
     * Create and register a development component. If the development component
     * is already registered the existing object will be returned.
     * 
     * @param name
     *            name of development component.
     * @param vendor
     *            vendor of development component.
     * @return a development component with the given vendor, name and type
     *         {@link DevelopmentComponentType#unknown}.
     */
    public DevelopmentComponent create(final String name, final String vendor) {
        return this.create(name, vendor, DevelopmentComponentType.unknown);
    }

    /**
     * Return all registered development components.
     * 
     * @return a collection of all registered development components.
     */
    public Collection<DevelopmentComponent> getAll() {
        final Collection<DevelopmentComponent> components = new ArrayList<DevelopmentComponent>(this.componentMap.size());
        components.addAll(this.componentMap.values());

        return components;
    }

    /**
     * Update the using DCs for all registered DCs.
     * 
     * For each registered development component the list of public parts it
     * references will be iterated. The respective development component will be
     * looked up and the currently worked on DC will be added to its using DCs.
     */
    public void updateUsingDCs() {
        for (final DevelopmentComponent component : this.componentMap.values()) {
            this.updateUsingDCs(component);
        }
    }

    /**
     * Updates the using DCs for the given DC.
     * 
     * @param root
     *            development component whose using DCs are to be updated.
     */
    private void updateUsingDCs(final DevelopmentComponent root) {
        for (final DevelopmentComponent dc : this.componentMap.values()) {
            final Collection<PublicPartReference> references = dc.getUsedDevelopmentComponents();

            for (final PublicPartReference reference : references) {
                if (root.getVendor().equals(reference.getVendor()) && root.getName().equals(reference.getComponentName())) {
                    root.addUsingDC(dc);

                    if (dc.getUsingDevelopmentComponents().size() == 0) {
                        this.updateUsingDCs(dc);
                    }
                }
            }
        }
    }

    /**
     * Return the development component matching the given vendor and component
     * name.
     * 
     * @param vendor
     *            vendor of development component.
     * @param name
     *            name of development component.
     * @return the development component asked for or <code>null</code> if it is
     *         not registered.
     */
    public DevelopmentComponent get(final String vendor, final String name) {
        return this.componentMap.get(createComponentKey(name, vendor));
    }
}