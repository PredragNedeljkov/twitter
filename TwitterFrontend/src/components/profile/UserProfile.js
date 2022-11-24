import React from "react";
import { useParams } from "react-router-dom";
import Profile from "./Profile";

function UserProfile(props) {
    const params = useParams();

    return <Profile requestUserId={params.userId} />
}

export default UserProfile;