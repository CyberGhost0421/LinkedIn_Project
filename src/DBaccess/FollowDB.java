package DBaccess;
import Models.Follow;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FollowDB {
    private final Connection connection;
    public FollowDB() throws SQLException {
        connection = DatabaseConnectionManager.getConnection();
        createFollowTable();
    }

    public void createFollowTable() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS follows (follower VARCHAR(36), followed VARCHAR(36), PRIMARY KEY (follower, followed))");
        preparedStatement.executeUpdate();
    }

    public void saveFollow(Follow follow) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO follows (follower, followed) VALUES (?, ?)");
        preparedStatement.setString(1, follow.getFollower());
        preparedStatement.setString(2, follow.getFollowed());
        preparedStatement.executeUpdate();
    }

    public void deleteFollow(Follow follow) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM follows WHERE follower = ? AND followed = ?");
        preparedStatement.setString(1, follow.getFollower());
        preparedStatement.setString(2, follow.getFollowed());
        preparedStatement.executeUpdate();
    }

    public void deleteAll() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM follows");
        preparedStatement.executeUpdate();
    }

    public List<Follow> getFollows(String userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM follows WHERE follower = ?");
        preparedStatement.setString(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Follow> follows = new ArrayList<>();
        while (resultSet.next()) {
            Follow follow = new Follow();
            follow.setFollower(resultSet.getString("follower"));
            follow.setFollowed(resultSet.getString("followed"));
            follows.add(follow);
        }
        return follows;
    }

    public List<Follow> getFollowers(String userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM follows WHERE followed = ?");
        preparedStatement.setString(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Follow> follows = new ArrayList<>();
        while (resultSet.next()) {
            Follow follow = new Follow();
            follow.setFollower(resultSet.getString("follower"));
            follow.setFollowed(resultSet.getString("followed"));
            follows.add(follow);
        }
        return follows;
    }

    public List<Follow> getAll() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM follows");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Follow> follows = new ArrayList<>();
        while (resultSet.next()) {
            Follow follow = new Follow();
            follow.setFollower(resultSet.getString("follower"));
            follow.setFollowed(resultSet.getString("followed"));
            follows.add(follow);
        }
        return follows;
    }

    public boolean isFollowing(String followerId, String followedId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM follows WHERE follower = ? AND followed = ?");
        preparedStatement.setString(1, followerId);
        preparedStatement.setString(2, followedId);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean isFollowing = resultSet.next();
        return isFollowing;
    }
}
