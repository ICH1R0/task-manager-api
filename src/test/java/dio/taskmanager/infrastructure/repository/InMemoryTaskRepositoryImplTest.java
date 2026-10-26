package dio.taskmanager.infrastructure.repository;

import dio.taskmanager.domain.*;

class InMemoryTaskRepositoryImplTest extends TaskRepositoryTest {

    @Override
    protected TaskRepository createRepository() {
        return new InMemoryTaskRepositoryImpl();
    }
}