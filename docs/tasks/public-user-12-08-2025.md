### public user

- [X] Any /public route should be accessible without authentication

- [ ] Should update user (name, email, title, about, phone, profile file, PROJECTS, TECHS, LINKS)
    - [X] PUT /user
  
- [ ] Should bind the project and user
    - [X] POST /user/project

- [ ] Should bind the tech and user
    - [X] POST /user/tech

- [ ] Should bind the link and user
  - POST /user/link

- [ ] Should retrieves user publicly
  - [X] GET /public/user
  
- [ ] Should retrieves all user projects publicly
    - [X] GET /public/user/{UUID}/project

- [ ] Should retrieves all user techs publicly
  - [X] GET /public/user/{UUID}/tech

- [ ] Should retrieves all user links publicly
  - [ ] GET /public/user/{UUID}/link

- [ ] Should unbind the project and user
    - DELETE /user/project/UUID

- [ ] Should unbind the tech and user
    - DELETE /user/tech/UUID

- [ ] Should unbind the link and user
    - DELETE /user/link/UUID