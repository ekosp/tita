/**
   Copyright 2009 TiTA Project, Vienna University of Technology
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE\-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package at.ac.tuwien.ifs.tita.business.service.user;

import java.util.List;

import javax.persistence.PersistenceException;

import at.ac.tuwien.ifs.tita.dao.exception.TitaDAOException;
import at.ac.tuwien.ifs.tita.dao.user.RoleDAO;
import at.ac.tuwien.ifs.tita.dao.user.UserDAO;
import at.ac.tuwien.ifs.tita.entity.TiTAUser;
import at.ac.tuwien.ifs.tita.entity.conv.Role;

/**
 * Service for manipulating (insert, update, delete, search... ) users and roles
 * in TiTA.
 *
 * @author ASE Group 10 - TiTA
 *
 */
public class UserService implements IUserService {

    private UserDAO userDao;
    private RoleDAO roleDao;

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void setRoleDao(RoleDAO roleDao) {
        this.roleDao = roleDao;
    }

    /** {@inheritDoc} */
    @Override
    public void deleteRole(Role role) throws PersistenceException {
        this.roleDao.delete(role);
    }

    /** {@inheritDoc} */
    @Override
    public void deleteUser(TiTAUser user) throws PersistenceException {
        this.userDao.delete(user);

    }

    /** {@inheritDoc} */
    @Override
    public Role getRoleById(Long id) throws PersistenceException {
        return this.roleDao.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    public TiTAUser getUserById(Long id) throws PersistenceException {
        return this.userDao.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    public Role saveRole(Role role) throws PersistenceException {
        return this.roleDao.save(role);
    }

    /** {@inheritDoc} */
    @Override
    public TiTAUser saveUser(TiTAUser user) throws PersistenceException {
        return this.userDao.save(user);
    }

    /** {@inheritDoc} */
    @Override
    public TiTAUser getUserByUsername(String username) throws TitaDAOException {
        return this.userDao.findByUserName(username);
    }


    /** {@inheritDoc} */
    @Override
    public List<TiTAUser> getUndeletedUsers() throws TitaDAOException {
        TiTAUser u = new TiTAUser();
        u.setDeleted(false);
        return this.userDao.findByExample(u);
    }

    /** {@inheritDoc} */
    @Override
    public List<Role> getRoles() throws TitaDAOException {
        return this.roleDao.findAll();
    }

    /** {@inheritDoc} */
    @Override
    public List<TiTAUser> findAllTiTAUsersForProjects(List<String> projects) {
        return userDao.findUsersForProjectNames(projects);
    }
}