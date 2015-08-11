/*
 * ImageLoader
 * https://github.com/mabi87/Android-ImageLoader
 *
 * Mabi
 * crust87@gmail.com
 * last modify 2015-08-11
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mabi87.imageloadersample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mabi87.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends ActionBarActivity {
	
	// List Components
	private ListView mListViewGame;
	private ArrayList<Game> mArrayListGame;
	private GameAdapter mGameAdapter;
	
	private ImageLoader mImageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mImageLoader = new ImageLoader(getApplicationContext(), true, true);
		
		// Load Layout Components
		setContentView(R.layout.activity_list);
		mListViewGame = (ListView) findViewById(R.id.listViewGame);
		
		loadList();
	}
	
	// Method for load list
	private void loadList() {
		mArrayListGame = new ArrayList<Game>();
		mGameAdapter = new GameAdapter(getApplicationContext(), mArrayListGame);
		mListViewGame.setAdapter(mGameAdapter);
		
		// on item click listener
		mListViewGame.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Game lGame = mGameAdapter.getItem(position);
				Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
				intent.putExtra("path", lGame.getImagePath());
				intent.putExtra("title", lGame.getTitle());
				intent.putExtra("content", lGame.getContent());
				
				startActivity(intent);
			}
		});
		
		// on scroll listener
		mListViewGame.setOnScrollListener(new OnScrollListener() {
			
			private boolean isLoading;
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (!isLoading && (totalItemCount - visibleItemCount) <= firstVisibleItem) {
					// add more item if last item visible
					addItem();
				}
			}
			
			// add 10 item to list
			private void addItem() {
				isLoading = true;
				ArrayList<Game> result = new ArrayList<Game>();
				result.add(new Game("Warcraft", "Warcraft is a franchise of video games, novels, and other media created by Blizzard Entertainment. The series is made up of five core games: Warcraft: Orcs & Humans, Warcraft II: Tides of Darkness, Warcraft III: Reign of Chaos, World of Warcraft, and Hearthstone: Heroes of Warcraft. The first three of these core games are in the real-time strategy genre, where opposing players command virtual armies in battle against each other or a computer-controlled enemy. The fourth and best-selling title of the franchise is a massively multiplayer online role-playing game (MMORPG), where players control their character and interact with each other in a virtual world. The most recent title is Hearthstone, a digital collectible card game.", "http://assets.vg247.com/current//2014/10/463146_2.jpg"));
				result.add(new Game("StarCraft", "StarCraft is a military science fiction real-time strategy video game developed and published by Blizzard Entertainment and released for Microsoft Windows on March 31, 1998. The game later spawned a franchise, and is the first game of the StarCraft series. A Mac OS version was released in 1999, and a Nintendo 64 adaptation co-developed with Mass Media was released on June 13, 2000. Work on the game started shortly after Warcraft II: Tides of Darkness�띯��'�땣 release in 1995. StarCraft debuted at the 1996 E3, where it was unfavorably compared to Warcraft II. As a result, the project was entirely overhauled and then showcased to public in early 1997, receiving a far more positive response.", "http://hdwpapers.com/thumbs/starcraft_2_wallpapers_always_on_my_overmind_wallpaper-t2.jpg"));
				result.add(new Game("Diablo", "Diablo is an action role-playing hack and slash video game developed by Blizzard North and released by Blizzard Entertainment on December 31, 1996.", "http://1.bp.blogspot.com/-JVSNTpinBv8/Ue1fJIoxCgI/AAAAAAAATeo/6f8F8uHzujA/s400/Diablo-3-Wallpaper-17.png"));
				result.add(new Game("Heroes of the Storm", "Heroes of the Storm (Originally titled Blizzard DOTA and later changed to Blizzard All-Stars) is a multiplayer online battle arena developed by Blizzard Entertainment for Microsoft Windows and OS X. The game features heroes from Blizzard's franchises including Warcraft, Diablo, and StarCraft. The game uses both free-to-play and freemium models and is supported by micropayments, which can be used to purchase heroes, visual alterations for the heroes in the game and mounts. Blizzard does not call the game a \"multiplayer online battle arena\" or an \"action real-time strategy\" because they feel it is something different with a broader playstyle; they refer to it as an online \"Hero Brawler\".", "http://extramania.com.pe/wp-content/uploads/2015/05/11-400x300.jpg"));
				result.add(new Game("Hearthstone", "Hearthstone: Heroes of Warcraft is an online collectible card game developed by Blizzard Entertainment. It is free-to-play with optional purchases to acquire additional cards and access content quicker. The game was announced at the Penny Arcade Expo in March 2013 and released on March 11, 2014. Hearthstone is available on Microsoft Windows and OS X systems and on Windows 8, iOS and Android touchscreen devices. New content for the game involves the addition of new card sets and gameplay, taking the form of either expansion packs or single-player adventures that reward the player with collectible cards upon completion.", "http://www.mmorpg.fr/logo/img/12857-logo-Hearthstone-Heroes-of-Warcraft.jpg"));
				result.add(new Game("The Lost Vikings", "The Lost Vikings is a puzzle-platform video game developed by Silicon & Synapse (now Blizzard Entertainment) and published by Interplay. It was originally released for the Super Nintendo Entertainment System in 1992, then subsequently released for the Amiga, Amiga CD32, MS-DOS, and Sega Mega Drive/Sega Genesis systems the next year; the Mega Drive/Genesis version contains five stages not present in any other version of the game. Blizzard re-released the game for the Game Boy Advance in 2003. The GBA port is identical to the SNES version, but the password feature has been removed and replaced with three save slots, no longer allowing the player to replay any level at any time.", "https://gamesthatrocked.files.wordpress.com/2013/07/the-lost-vikings.jpg?w=400"));
				result.add(new Game("Overwatch", "Overwatch is an upcoming multiplayer first-person shooter in development by Blizzard Entertainment. Unveiled on November 7, 2014 at BlizzCon, the game emphasizes cooperative gameplay using a cast of various \"heroes\", each with their own abilities and roles within a team. The game is scheduled to enter a closed beta phase in the fall of 2015.", "https://8583b52b4a309671f69d-b436b898353c7dc300b5887446a26466.ssl.cf1.rackcdn.com/3355869_56b8ebd8_m.jpeg"));	
				result.add(new Game("SimCity", "SimCity is an open-ended city-building computer and console video game series originally designed by developer Will Wright. It is published by Maxis (now a division of Electronic Arts). The game was first published in 1989 as SimCity, and it has spawned several different editions sold worldwide. The ongoing success of SimCity has also sparked the release of many other spin-off \"Sim\" titles, including 2000's The Sims, one of the best-selling computer games", "http://4.bp.blogspot.com/-fEos3fTNrdc/UJZf7c3zTcI/AAAAAAAAAfQ/kng3IwjYzrQ/s400/sim+city+1.png"));
				result.add(new Game("The Sims", "The Sims is a strategic life-simulation video game developed by Maxis, published by Electronic Arts, and released on February 4, 2000. The game's development was led by game designer Will Wright, also known for developing SimCity. It is a simulation of the daily activities of one or more virtual persons (\"Sims\") in a suburban household near SimCity.", "http://games-open.com/wp-content/uploads/2013/09/sims-2-pc-gamedownload-the-sims-2-full-version-gubuk-software-uqrc88l0.jpg"));
				result.add(new Game("Civilization", "Sid Meier's Civilization is a turn-based \"4X\"-type strategy video game created by Sid Meier and Bruce Shelley for MicroProse in 1991. The game's objective is to \"Build an empire to stand the test of time\": it begins in 4000 BC and the players attempt to expand and develop their empires through the ages from the ancient era until modern and near-future times. It is also known simply as Civilization, Civilization I, or abbreviated to Civ or Civ I.", "http://www.userlogos.org/files/logos/Mafia_Penguin/civ5.png"));
				
				mArrayListGame.addAll(result);
				mGameAdapter.notifyDataSetChanged();
				isLoading = false;
			}
		});
	}
	
	// List Adapter
	private class GameAdapter extends ArrayAdapter<Game> {
			
		// Components
		private LayoutInflater mInflater;
			
		public GameAdapter(Context context, List<Game> objects) {
			super(context, 0, objects);
			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;

			if (convertView == null) {
				// inflate new layout object
				view = mInflater.inflate(R.layout.row_game, null);
			} else {
				view = convertView;
			}
				
			Game data = this.getItem(position);
				
			if (data != null) {
				// get layout components
				ImageView imageView = ViewHolderHelper.get(view, R.id.imageViewGame);
				TextView textViewTitle = ViewHolderHelper.get(view, R.id.textViewTitle);
				TextView textViewContent = ViewHolderHelper.get(view, R.id.textViewContent);
				
				// set contents
				mImageLoader.loadImage(imageView, data.getImagePath());
				textViewTitle.setText(data.getTitle());
				textViewContent.setText(data.getContent());
			}
				
			return view;
		}
			
	}
		
}
