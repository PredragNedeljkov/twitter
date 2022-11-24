import React, {useState, useEffect, useRef} from 'react';
import { getProfileAndPosts, saveNewComment, likePost, dislikePost, getImagesURL, followUser, acceptFollowingRequest, declineFollowingRequest } from './ProfileService';
import { connect } from 'react-redux';
import Post from '../post/Post';
import { saveNewPost } from '../post/PostService';
import { faImage} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

function Profile(props) {

    const [profile, setProfile] = useState({
        name: "",
        lastName: "",
        posts: [],
    });

    const newPostText = useRef();
    const imageFile = useRef(null);
    const smallImage = useRef(null);
    const [imageLoaded, setImageLoaded] = useState(false);
    const [urlError, setUrlError] = useState(false);

    useEffect(() => {
        const userId = props.requestUserId ? props.requestUserId : props.userId;
        getProfileAndPosts(userId, props.token).then(response => {
            console.log(response)
            setProfile({
                name: response.data.userName,
                lastName: response.data.userLastName,
                posts: response.data.posts
            });
        });
    }, []);

    function savePostHandler() {
        const postText = newPostText.current.value;
        if (postText === "" && urlError) {
            return;
        }

        const userId = props.requestUserId ? props.requestUserId : props.userId;
        const requestBody = new FormData(); 
        requestBody.append('file', imageFile.current.files[0]);
        requestBody.append('content', postText);
        requestBody.append('userId', userId);
        requestBody.append('userName', profile.name);
        requestBody.append('userLastName',profile.lastName);

        saveNewPost(requestBody, props.token).then(response => {
            newPostText.current.value = "";
            getProfileAndPosts(userId, props.token).then(response => {
                setProfile({
                    name: response.data.name,
                    lastName: response.data.lastName,
                    posts: response.data.posts,
                    requests: response.data.requests
                });
                setImageLoaded(false);
            });
        });
    }

    function handleLikePost(postId) {
        const requestBody = {
            userId: props.userId,
            userName: props.userName,
            userLastName: props.userLastName,
            postId
        };

        return likePost(requestBody, props.token);
    }

    function addImageButtonClickHandler() {
       imageFile.current.click();
    };

    function handleImageSelected(event) {
        const reader = new FileReader();
		reader.readAsDataURL(event.target.files[0]);
		reader.onload = function (e) {
            const image=new Image();
            image.src=e.target.result;
            image.onload = function () {
                smallImage.current.src=image.src;
            };
        };
        setImageLoaded(true);
    }
    
    return (
        <div className="body-replica" style={{color: "#797979", fontFamily: "'Open Sans', sans-serif", padding: "0px !important", margin: "0px !important", fontSize: "13px", textRendering: "optimizeLegibility", WebkitFontSmoothing: "antialiased", MozFontSmoothing: "antialiased", MarginTop: "20px"}}>
            <div className="container bootstrap snippets bootdey">
                <div className="row">
                <div className="panel" style={{width: "100%", marginBottom: "20px"}}>
                    <div className="cover-photo" style={{position: 'relative'}}>
                        <div className="fb-timeline-img">
                            <img src="https://g.foolcdn.com/editorial/images/451536/twitter-bird-on-blue-background.png" alt="" style={{width: '100%', height: 'auto', maxHeight: '280px', borderRadius: '4px 4px 0 0', WebkitBorderRadius: '4px 4px 0 0'}} />
                        </div>
                        <div className="fb-name" style={{bottom: '5px', left: '175px', position: 'absolute'}}>
                            <h2><span style={{color: '#FFFFFF', textRendering: 'optimizelegibility', textShadow: '0 0 3px rgba(0, 0, 0, 0.8)', fontSize: '25px'}}>{profile.name} {profile.lastName}</span></h2>
                        </div>
                    </div>

                    <div className="panel-body" style={{background: '#fff', padding: "0 15px"}}>

                        <div className="profile-thumb" style={{float: 'left', position: 'relative'}}>
                            <img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="" style={{width: '140px', height: '140px', borderRadius: '50%', WebkitBorderRadius: '50%', marginTop: '-90px', border: '3px solid #fff'}} />
                        </div>

                    </div>
                </div>

                <div className="panel profile-info" style={{width: "100%", marginBottom: '20px'}} hidden={props.requestUserId}>
                    <form>
                        <textarea ref={newPostText} className="form-control input-lg p-text-area" rows={2} placeholder="O čemu razmišljate?" style={{border: 'none', fontWeight: 300, boxShadow: 'none', color: '#c3c3c3', fontSize: '16px'}} defaultValue={""} />
                    </form>

                    <div>
                        <img ref={smallImage} className="img-fluid" width={200} height={200} hidden={!imageLoaded} />
                    </div>

                    <footer className="panel-footer" style={{"padding":"10px 15px","backgroundColor":"#f5f5f5","borderTop":"1px solid #ddd","borderBottomRightRadius":"3px","borderBottomLeftRadius":"3px"}}>
                        <button type="button" className="btn btn-info pull-right mr-2" style={{float: "right !important"}} onClick={savePostHandler}>Post</button>
                        <button type="button" className="btn btn-info pull-right mr-2" style={{float: "right !important"}} onClick={addImageButtonClickHandler}><FontAwesomeIcon className="h5 m-0" icon={faImage} /></button>
                        <input type='file' accept=".jpg,.jpeg,.png" id='file' ref={imageFile} style={{display: 'none'}} onChange={handleImageSelected} />
                    </footer>
                </div>
                
                
                <>
                    {profile.posts.length === 0 && <h5 className="mt-5">Nema dosadašnjih objava.</h5>}
                    {profile.posts.map(post => <Post key={post.id} post={post} currentUserId={props.userId} commentingAllowed={props.userId !== null} 
                                                        likePost={handleLikePost} getImagesURL={getImagesURL} />)}
                </>
                
                </div>
            </div>
        </div>
    )
}


function mapStateToProps(state) {
    return {
        userId: state.userId,
        token: state.token,
        userName: state.userName,
        userLastName: state.userLastName,
    }
}

export default connect(mapStateToProps)(Profile);