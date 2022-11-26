import React, {useState, useEffect} from "react";

function Post(props) {

    const [likes, setLikes] = useState([]);

    useEffect(() => {
        setLikes(props.post.likes);
    }, []);
    

    function handleLikeClick() {
        props.likePost(props.post.id).then(response => {
            setLikes(response.data.likes);
        });
    }

    const isLikedByCurrentUser = likes.some(like => like.userId === props.currentUserId);
    const likeButtonClass = isLikedByCurrentUser ? "btn btn-primary" : "btn btn-outline-primary";
    const likesText = isLikedByCurrentUser ? `Vama i još ${likes.length - 1} se sviđa ovaj post` : `${likes.length} korsinika voli ovaj post.`;

    return (
        <div className="panel w-100 mb-2">
            <div className="panel-body" style={{background: '#fff', padding: "15px"}}>
            <div className="fb-user-thumb" style={{float: 'left', width: '70px', marginRight: '15px'}}>
                <img src="https://www.w3schools.com/howto/img_avatar2.png" alt="" style={{width: '70px', height: '70px', borderRadius: '50%', WebkitBorderRadius: '50%'}} />
            </div>

            <div className="fb-user-details">
                <h3 style={{margin: '15px 0 0', fontSize: '18px', fontWeight: 300}}><span>{props.post.userName} {props.post.userLastName}</span></h3>
                <p style={{color: '#c3c3c3'}}>{props.post.timestamp}</p>
            </div>

            <div className="clearfix" />
                <p className="fb-user-status" style={{padding: '10px 0', lineHeight: '20px'}}>{props.post.content}</p>

                {props.post.imagePath && <img className="img-fluid" src={"data:image/png;base64, " + props.post.imagePath} height="500px" width="100%" />}

                {props.post.link && <a href={props.post.link} target="_blank">{props.post.link}</a>}

                <div className="fb-status-container fb-border" style={{borderTop: '1px solid #ebeef5', margin: '0 -15px 0 -15px', padding: '0 15px'}}>
                    <div className="fb-time-action" style={{padding: '15px 0'}}>
                    <button className={likeButtonClass} onClick={handleLikeClick}>Like</button>
                    <span style={{marginRight: '10px', color: '#5a5a5a'}}></span>
                </div>
            </div>

            <div className="fb-status-container fb-border fb-gray-bg" style={{borderTop: '1px solid #ebeef5', margin: '0 -15px 0 -15px', padding: '0 15px', background: '#f6f6f6'}}>
                <div className="fb-time-action like-info" style={{padding: '15px 0'}}>
                    <span style={{marginRight: '5px', color: '#2972a1'}}>{likesText}</span>
                </div>

                <div className="clearfix" /></div>
            </div>
        </div>
    )
}

export default Post;