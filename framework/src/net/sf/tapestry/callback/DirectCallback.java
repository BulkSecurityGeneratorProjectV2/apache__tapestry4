//
// Tapestry Web Application Framework
// Copyright (c) 2000-2002 by Howard Lewis Ship
//
// Howard Lewis Ship
// http://sf.net/projects/tapestry
// mailto:hship@users.sf.net
//
// This library is free software.
//
// You may redistribute it and/or modify it under the terms of the GNU
// Lesser General Public License as published by the Free Software Foundation.
//
// Version 2.1 of the license should be included with this distribution in
// the file LICENSE, as well as License.html. If the license is not
// included with this distribution, you may find a copy at the FSF web
// site at 'www.gnu.org' or 'www.fsf.org', or you may write to the
// Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139 USA.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied waranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//

package net.sf.tapestry.callback;

import net.sf.tapestry.IComponent;
import net.sf.tapestry.IDirect;
import net.sf.tapestry.IPage;
import net.sf.tapestry.IRequestCycle;
import net.sf.tapestry.RequestCycleException;
import net.sf.tapestry.Tapestry;

/**
 *  Simple callback for re-invoking a {@link IDirect} component trigger..
 *
 *  @version $Id$
 *  @author Howard Lewis Ship
 *  @since 0.2.9
 *
 **/

public class DirectCallback implements ICallback
{
    private String pageName;
    private String componentIdPath;
    private String[] parameters;

    public String toString()
    {
        StringBuffer buffer = new StringBuffer("DirectCallback[");

        buffer.append(pageName);
        buffer.append('/');
        buffer.append(componentIdPath);

        if (parameters != null)
        {
            char sep = ' ';

            for (int i = 0; i < parameters.length; i++)
            {
                buffer.append(sep);
                buffer.append(parameters[i]);

                sep = '/';
            }
        }

        buffer.append(']');

        return buffer.toString();

    }

    /**
     *  Creates a new DirectCallback for the component.  The context
     *  (which may be null) is retained, not copied.
     *
     **/

    public DirectCallback(IDirect component, String[] parameters)
    {
        pageName = component.getPage().getName();
        componentIdPath = component.getIdPath();
        this.parameters = parameters;
    }

    /**
     *  Locates the {@link IDirect} component that was previously identified
     *  (and whose page and id path were stored).
     *  Invokes {@link IRequestCycle#setServiceParameters(String[])} to
     *  restore the service parameters, then
     *  invokes {@link IDirect#trigger(IRequestCycle)} on the component.
     *
     **/

    public void performCallback(IRequestCycle cycle) throws RequestCycleException
    {
        IPage page = cycle.getPage(pageName);
        IComponent component = page.getNestedComponent(componentIdPath);
        IDirect direct = null;

        try
        {
            direct = (IDirect) component;
        }
        catch (ClassCastException ex)
        {
            throw new RequestCycleException(
                Tapestry.getString("DirectCallback.wrong-type", component.getExtendedId()),
                component,
                ex);
        }

        cycle.setServiceParameters(parameters);
        direct.trigger(cycle);
    }
}