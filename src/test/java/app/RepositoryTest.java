package app;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class RepositoryTest {

    @Test
    void fetchTableColumnsData_should_return_table_with_foreign_key() throws SQLException {
        //given
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        PreparedStatement statement = Mockito.mock(PreparedStatement.class);
        Connection connection = Mockito.mock(Connection.class);

        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(resultSet);

        //when


        //then
    }

}