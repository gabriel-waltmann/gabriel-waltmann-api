### public user

- [X] Any /public route should be accessible without authentication

- [ ] Should update user (name, email, title, about, phone, profile file)
    - [X] PUT /user
  
- [ ] Should bind the project and user
    - POST /user/project

- [ ] Should bind the tech and user
    - POST /user/tech

- [ ] Should bind the link and user
    - POST /user/link

- [ ] Should unbind the project and user
    - DELETE /user/project/UUID

- [ ] Should unbind the tech and user
    - DELETE /user/tech/UUID

- [ ] Should unbind the link and user
    - DELETE /user/link/UUID
    
- [ ] Should return publicly the user (name, email, title, about, phone, profile file, projects, techs, links)
    - [X] GET /public/user