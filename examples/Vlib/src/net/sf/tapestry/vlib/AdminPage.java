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

package net.sf.tapestry.vlib;

import net.sf.tapestry.IRequestCycle;
import net.sf.tapestry.PageRedirectException;
import net.sf.tapestry.RequestCycleException;
import net.sf.tapestry.callback.PageCallback;
import net.sf.tapestry.vlib.pages.Login;

/**
 *  Base page for any pages restricted to administrators.
 *
 *  @author Howard Lewis Ship
 *  @version $Id$
 * 
 **/

public class AdminPage extends Protected
{
    private String _message;

    public void detach()
    {
        _message = null;

        super.detach();
    }

    public String getMessage()
    {
        return _message;
    }

    public void setMessage(String value)
    {
        _message = value;
    }

    public void validate(IRequestCycle cycle) throws RequestCycleException
    {
        Visit visit = (Visit) getEngine().getVisit();

        if (visit == null || !visit.isUserLoggedIn())
        {
            Login login = (Login) cycle.getPage("Login");

            login.setCallback(new PageCallback(this));

            throw new PageRedirectException(login);
        }

        if (!visit.getUser().isAdmin())
        {
            visit.getEngine().presentError("That function is restricted to adminstrators.", cycle);

            throw new PageRedirectException(cycle.getPage());
        }
    }
}